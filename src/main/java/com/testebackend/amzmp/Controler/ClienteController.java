package com.testebackend.amzmp.Controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testebackend.amzmp.Model.Cliente;
import com.testebackend.amzmp.Repository.ClienteRepository;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;







@Controller
@RequestMapping("/amz-mp/cliente")
public class ClienteController {
    List<Cliente> listCliente = new ArrayList<Cliente>();

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Carregar a listagem clientes

    @GetMapping("/listar-cliente")
    public String ListarCliente(Model model) {
        model.addAttribute("cliente" , clienteRepository.findAll());
        return "cliente";
    }

    //Carregar o formulario cadastro
    
    @GetMapping("/novo-cliente")
    public String CadastrarCliente(Cliente cliente, Model model) {
        model.addAttribute("cliente", cliente);
        return "novo-cliente";
    }
    
    //Cadastrar (inserir)

    @PostMapping("/add-cliente")
    public String addClinte(Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
                return "/novo-cliente";
        }

        Cliente clienteDb = clienteRepository.save(cliente);
        cliente.setStatus_cliente(true);

        return "redirect:/amz-mp/cliente/listar-cliente";
    }
    
    //Carregar edição de cliente

    @GetMapping("/editar/{id}")
    public String showUpadateform(@PathVariable("id") Long id, ModelMap model) {
        Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid cliente id:" + id));

        model.addAttribute("cliente", cliente);
        return "novo-cliente";
    }

    @PostMapping("/update/{id}")
	public String atualizarCliente(
	    @PathVariable("id") long id,
		@ModelAttribute("cliente") Cliente cliente, BindingResult result){
			if(result.hasErrors()) {
				cliente.setId(id);
				return "novo-cliente";
			}
			
		clienteRepository.save(cliente);
		return "redirect:/uniao-voluntaria/usuario/listar-usuario";
	}
    

}
