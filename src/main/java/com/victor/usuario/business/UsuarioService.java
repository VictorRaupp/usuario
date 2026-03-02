package com.victor.usuario.business;


import com.victor.usuario.business.converter.UsuarioConverter;
import com.victor.usuario.business.dto.EnderecoDTO;
import com.victor.usuario.business.dto.TelefoneDTO;
import com.victor.usuario.business.dto.UsuarioDTO;
import com.victor.usuario.infrastructure.entity.Endereco;
import com.victor.usuario.infrastructure.entity.Telefone;
import com.victor.usuario.infrastructure.entity.Usuario;
import com.victor.usuario.infrastructure.exceptions.ConflictExeption;
import com.victor.usuario.infrastructure.exceptions.ResourceNotFoundExecption;
import com.victor.usuario.infrastructure.repository.EnderecoRepository;
import com.victor.usuario.infrastructure.repository.TelefoneRepository;
import com.victor.usuario.infrastructure.repository.UsuarioRepository;
import com.victor.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getSenha());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new ConflictExeption("Email já cadastrado " + email);
            }
        }catch (ConflictExeption e){
            throw new ConflictExeption("Email já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try{
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.findByEmail(email)
                        .orElseThrow(
                () -> new ResourceNotFoundExecption("Email não encontrado "+email)));
    }catch (ResourceNotFoundExecption e){
            throw new ResourceNotFoundExecption("Email não encontrado "+email);
        }
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario (String token, UsuarioDTO dto){
        //buscar email do usuario atrasves do token
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //se o usuario nao passara senha volta nulo se passar criptograsamos a
        // senha novamente
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        // buscar dados do usuario no banco de dados
        Usuario usuarioEntity =  usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundExecption("Email não encontrado"));

        //mesclou oss dados que recebemos na requisição DTO com os dados do banco de dados
        Usuario usuario =  usuarioConverter.updateUsuario(dto, usuarioEntity);

        //salvou os dados do usuario convertido e depois pegou o retorno
        // e converteu para UsuarioDto
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }
    public EnderecoDTO cadastraEndereco (String token, EnderecoDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundExecption("Email não encontrado "+email));

        Endereco endereco =usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastraTelefone (String token,TelefoneDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundExecption("Email não encontrado "+email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(()->
                new ResourceNotFoundExecption("Id não encontrado "+idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(()->
                new ResourceNotFoundExecption("Id não encontrado "+idTelefone));
        Telefone telefone = usuarioConverter.uptadeTelefone(telefoneDTO,entity);

        return  usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }



}
