package vttp2022.paf.giphy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.paf.giphy.services.GiphyService;


@Controller
@RequestMapping
public class GiphyController {

    @Autowired
    private GiphyService gSvc;

    @GetMapping(path="/search")
    public String getGiphy(Model model, @RequestParam String q, 
        @RequestParam Integer limit, @RequestParam String rating) {
        System.out.printf(">>> q: %s, limit: %d, rating: %s\n", q, limit, rating);
        List<String> giphys = gSvc.getGiphy(q, limit, rating);
        model.addAttribute("q", q);
        model.addAttribute("giphys", giphys);
        return "result";
    }
    
}
