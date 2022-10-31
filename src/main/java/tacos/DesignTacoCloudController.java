package tacos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.server.EntityLinks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.repo.TacoRepository;

import java.util.Optional;


@RestController
@RequestMapping(path = "/design", produces = "application/json")
@Slf4j
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DesignTacoCloudController {

    private final TacoRepository tacoRepo;


    @Autowired
    EntityLinks entityLinks;

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        return tacoRepo.findAll(pageRequest).getContent();
    }



    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepo.findById(id);
        if (optionalTaco.isPresent())
            return new ResponseEntity<>(optionalTaco.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
