package br.com.brq.projetoecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjetoEcommerceApplicationTests {
	
	@Test
	void somarValidTeste() {
		int a = 0;
		int b = 1;
		
		int c = a + b;
		
		assertEquals(1, c);
	}
	
	@Test
	void somarInvalidTeste() {
		int a = 0;
		int b = 1;
		
		int c = a + b;
		
		assertThat(c > 0).isTrue();		
	}
}
