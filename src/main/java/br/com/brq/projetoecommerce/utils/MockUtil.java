package br.com.brq.projetoecommerce.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brq.projetoecommerce.domain.CategoriaEntity;
import br.com.brq.projetoecommerce.domain.EnderecoEntity;
import br.com.brq.projetoecommerce.domain.ImagemEntity;
import br.com.brq.projetoecommerce.domain.ItemVendaEntity;
import br.com.brq.projetoecommerce.domain.ProdutoEntity;
import br.com.brq.projetoecommerce.domain.UsuarioEntity;
import br.com.brq.projetoecommerce.domain.VendaEntity;
import br.com.brq.projetoecommerce.dto.CategoriaDTO;
import br.com.brq.projetoecommerce.dto.EnderecoDTO;
import br.com.brq.projetoecommerce.dto.ImagemDTO;
import br.com.brq.projetoecommerce.dto.ItemVendaDTO;
import br.com.brq.projetoecommerce.dto.ProdutoDTO;
import br.com.brq.projetoecommerce.dto.UsuarioDTO;
import br.com.brq.projetoecommerce.dto.VendaDTO;
import br.com.brq.projetoecommerce.services.CategoriaService;
import br.com.brq.projetoecommerce.services.EnderecoService;
import br.com.brq.projetoecommerce.services.ImagemService;
import br.com.brq.projetoecommerce.services.ItemVendaService;
import br.com.brq.projetoecommerce.services.ProdutoService;
import br.com.brq.projetoecommerce.services.UsuarioService;
import br.com.brq.projetoecommerce.services.VendaService;

@Component
public class MockUtil {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private ItemVendaService itemVendaService;

	@Autowired
	private VendaService vendaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EnderecoService enderecoService;

	public CategoriaDTO categoriaControllerMock() {

		CategoriaEntity categoriaMocka = categoriaService
				.salvar(CategoriaEntity.builder().nomeCategoria("Eletronicos").build());
		return categoriaMocka.toDTO();
	}

	public ImagemDTO imagemControllerMock() {

		ImagemEntity imagemMock = imagemService.salvar(ImagemEntity.builder().imagemProduto("(*-*)").build());
		return imagemMock.toDTO();
	}

	public ProdutoDTO produtoControllerMock() {

		CategoriaDTO categoriaMock = this.categoriaControllerMock(); 

		List<CategoriaEntity> listCat = new ArrayList<>();
		listCat.add(categoriaMock.toEntity());

		ImagemDTO imagemMock = this.imagemControllerMock();

		List<ImagemEntity> listIma = new ArrayList<>();
		listIma.add(imagemMock.toEntity());

		ProdutoEntity produtoMock = produtoService.salvar(ProdutoEntity.builder().nome("Xiaomi").preco(2000)
				.descricao("Celular").categorias(listCat).imagens(listIma).build());

		return produtoMock.toDTO();
	}

	public ItemVendaDTO itemControllerMock() {

		ProdutoDTO produtoMock = this.produtoControllerMock();
		List<ProdutoEntity> listProd = new ArrayList<>();
		listProd.add(produtoMock.toEntity());

		ItemVendaEntity itemMock = itemVendaService
				.salvar(ItemVendaEntity.builder().itemQuantidade(1).itemProduto(listProd).build());

		return itemMock.toDTO();
	}

	private UsuarioDTO usuarioMock() {
		EnderecoDTO dto = enderecoMock();
		List<EnderecoEntity> listDto = new ArrayList<>();
		listDto.add(dto.toEntity());
		UsuarioEntity enty = usuarioService.salvar(
				UsuarioEntity.builder().nome("Karina").cpf("12345678911").celular("11222223333").telefone("22223333")
						.email("boladinhos@gmail.com").dataDeNascimento("1998, 05, 18").enderecos(listDto).build());
		return enty.toDTO();
	}

	private EnderecoDTO enderecoMock() {
		EnderecoEntity enty = enderecoService.salvar(EnderecoEntity.builder().enderecoId(1)
				.logradouro("Rua do Anderson").numero("55").complemento("ap 12").cep("91234-567")
				.cidade("Cidade do Anderson").bairro("Bairro dos boladinhos").estado("estado laico").build());

		return enty.toDTO();
	}

	public VendaDTO vendaControllerMock() {

		ItemVendaDTO itemMock = this.itemControllerMock();
		List<ItemVendaEntity> listItem = new ArrayList<>();
		listItem.add(itemMock.toEntity());

		UsuarioDTO uDto = usuarioMock();
		UsuarioEntity uEnty = uDto.toEntity();

		VendaEntity vendaMock = vendaService
				.salvar(VendaEntity.builder().dataVenda("24-12-2021").itens(listItem).usuario(uEnty).build());

		return vendaMock.toDTO();
	}

	public CategoriaEntity categoriaMock() {
		return CategoriaEntity.builder().nomeCategoria("Eletronico").build();
	}

	public ImagemEntity imagemMock() {
		return ImagemEntity.builder().imagemProduto("nlousafvcijiuasfv").build();
	}

	public ItemVendaEntity itemVendaMock() {
		return ItemVendaEntity.builder().idItemVenda(2).itemQuantidade(1).build();
	}

	public ProdutoEntity produtoMock() {

		CategoriaDTO categoriaMock = this.categoriaControllerMock();

		List<CategoriaEntity> listCat = new ArrayList<>();
		listCat.add(categoriaMock.toEntity());

		ImagemDTO imagemMock = this.imagemControllerMock();

		List<ImagemEntity> listIma = new ArrayList<>();
		listIma.add(imagemMock.toEntity());

		return ProdutoEntity.builder().nome("Xiaomi").preco(2000).descricao("Celular Chines").categorias(listCat)
				.imagens(listIma).build();
	}

	public VendaEntity vendaMock() {
		return VendaEntity.builder().build();
	}
}
