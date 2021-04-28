package br.com.springcontatos.contatosSpringBoot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.UUID;

@Controller
public class ContatoControle {

    private  static  final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();

   //Contatos staticos
    static {

   }
    //Pagina inicial
    @GetMapping("/")
    public String index(){
        return "index";
    }
    //Listando os Contatos
    @GetMapping("/contatos")
    public ModelAndView listar(){
        ModelAndView modelAndView = new ModelAndView("listar");
        modelAndView.addObject("contatos", LISTA_CONTATOS);
        return modelAndView;
    }

    //Cadastro de contatos NOVO
    @GetMapping("/contatos/novo")
    public ModelAndView novo(){
        ModelAndView modelAndView = new ModelAndView("formulario");
        modelAndView.addObject("contato", new Contato());
        return modelAndView;
    }


    @PostMapping(value = "/contatos")
    public String cadastrar(Contato contato){
        //Controle dos IDs é de responsabilidade do sistema.
        String id = UUID.randomUUID().toString();
        contato.setId(id);
        LISTA_CONTATOS.add(contato);
        //redirecionamento para pagina de contatos, feita pelo brownse
        return "redirect:/contatos";
    }

    //Requisição feita pelo PathVariable e identificar qual ID esta posicionado
    @GetMapping("/contatos/{id}/editar")
    public ModelAndView editar(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView("formulario");

        Contato contato = procurarContato(id);

        modelAndView.addObject("contato",contato);
        return modelAndView;
    }
    //Removendo e alterando
    @PutMapping("/contatos/{id}")
    public String atualizar(Contato contato){
        Integer indice = procurarIndiceContato(contato.getId());

        Contato contatoOld = LISTA_CONTATOS.get(indice);

        LISTA_CONTATOS.remove(contatoOld);

        LISTA_CONTATOS.add(indice, contato);

        return "redirect:/contatos";
    }
    //metodo remover
    @DeleteMapping("/contatos/{id}")
    public String remover(@PathVariable String id){
        Contato contato = procurarContato(id);

        LISTA_CONTATOS.remove(contato);

        return "redirect:/contatos";
    }

//==============Metodos auxiliares=============

    //pega o indice da regra abaixo e utiliza o indice
    private Contato procurarContato( String id){
        Integer indice = procurarIndiceContato(id);

        if (indice != null){
            Contato contato = LISTA_CONTATOS.get(indice);
            return  contato;
        }
        //caso nao encontre o ID
        return null;
    }
    //Regra que procura o contato
    private Integer procurarIndiceContato(String id){
        for(int i=0; i< LISTA_CONTATOS.size(); i++){
            Contato contato = LISTA_CONTATOS.get(i);

            //compara o id de contato com o que foi solicitado
            if(contato.getId().equals(id)) {
                return i;
            }
        }
        //caso nao encontre o ID
        return null;
    }

}
