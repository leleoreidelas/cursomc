package com.startmob.cursomc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.startmob.cursomc.domain.Categoria;
import com.startmob.cursomc.domain.Cidade;
import com.startmob.cursomc.domain.Cliente;
import com.startmob.cursomc.domain.Endereco;
import com.startmob.cursomc.domain.Estado;
import com.startmob.cursomc.domain.ItemPedido;
import com.startmob.cursomc.domain.Pagamento;
import com.startmob.cursomc.domain.PagamentoComBoleto;
import com.startmob.cursomc.domain.PagamentoComCartao;
import com.startmob.cursomc.domain.Pedido;
import com.startmob.cursomc.domain.Produto;
import com.startmob.cursomc.domain.enums.EstadoPagamento;
import com.startmob.cursomc.domain.enums.TipoCliente;
import com.startmob.cursomc.repositories.CategoriaRepository;
import com.startmob.cursomc.repositories.CidadeRepository;
import com.startmob.cursomc.repositories.ClienteRepository;
import com.startmob.cursomc.repositories.EnderecoRepository;
import com.startmob.cursomc.repositories.EstadoRepository;
import com.startmob.cursomc.repositories.ItemPedidoRepository;
import com.startmob.cursomc.repositories.PagamentoRepository;
import com.startmob.cursomc.repositories.PedidoRepository;
import com.startmob.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletronicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");
        
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 900.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Vitor Reis", "vitor@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("38275585", "19823782737"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Leoroy", "341", "Sala 80", "Centro", "382205677", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		Pedido ped1 = new Pedido(null, getData("30/09/2017 10:32"), cli1, e1);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
//
		Pedido ped2 = new Pedido(null, getData("02/04/2017 19:40"), cli1, e2);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENDTE, ped2, getData("21/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);
//
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
//
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
        
        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));
        
        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));
        
        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
	
	public Date getData(String dataString) throws ParseException {
		SimpleDateFormat ftm = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
		Date dataRetornada = ftm.parse(dataString);
		return dataRetornada;
	}
}
