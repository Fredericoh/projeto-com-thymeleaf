package br.edu.faculdadedelta.projetothymeleaf.controller;

import br.edu.faculdadedelta.projetothymeleaf.modelo.Evento;
import br.edu.faculdadedelta.projetothymeleaf.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final String PAGINA_CADASTRO = "evento";
    private final String PAGINA_LISTA = "listaEvento";

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping(value = "/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView (PAGINA_CADASTRO);
        mv.addObject(new Evento ());
        return mv;
    }

    @PostMapping
    public ModelAndView incluir(@Validated Evento evento, RedirectAttributes redirectAttributes, Errors errors){

        if (errors.hasErrors ()){
            return new ModelAndView (PAGINA_CADASTRO);
        }

        if (evento.getIdEvento () == null)
            redirectAttributes.addFlashAttribute ("mensagem", "Inclusão realizada com sucesso.");
         else
            redirectAttributes.addFlashAttribute ("mensagem", "Alteração realizada com sucesso.");

         eventoRepository.save (evento);

        return new ModelAndView ("redirect:/eventos/novo");
    }

    @GetMapping
    public ModelAndView listar(){
        ModelAndView modelAndView = new ModelAndView (PAGINA_LISTA);

        List<Evento> eventos = eventoRepository.findAll ();

        modelAndView.addObject ("eventos", eventos);

        return modelAndView;
    }

    @GetMapping(value = "/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id){
        eventoRepository.deleteById (id);

        return new ModelAndView ("redirect:/eventos");
    }

    @GetMapping(value = "/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Evento evento = eventoRepository.findById(id).orElseThrow(()-> new EmptyResultDataAccessException (1));
        ModelAndView modelAndView = new ModelAndView (PAGINA_CADASTRO);
        modelAndView.addObject (evento);

        return modelAndView;
    }
}
