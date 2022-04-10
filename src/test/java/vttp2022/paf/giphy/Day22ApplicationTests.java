package vttp2022.paf.giphy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.paf.giphy.services.GiphyService;

@SpringBootTest
class Day22ApplicationTests {

	@Autowired
	private GiphyService gSvc;

	@Test
	void shouldLoad5Images() {
		List<String> gifs = gSvc.getGiphy("arcane", 5, "R");
		assertEquals(5, gifs.size(), "gif size: %s".formatted(gifs.size()));
	}

}
