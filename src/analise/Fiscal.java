/* *******************************************************************************************************
 * Código referente a importação e manipulação de dados, do programa de validações fiscais.              *
 * Versão 1.0                                                                                            *
 * Utiliza arquivos txt gerados a partir do programa Mastersaf Smart, analíticos, codificados em UTF-8,  * 
 * com seus dados separados por tabulações:                                                              *
 * - NF relação notas - Entrada e Saída                                                                  *
 * - NF planilha notas - Entradas                                                                        *
 * - NF planilha notas - Saídas                                                                          *
 * *******************************************************************************************************/

package analise;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Fiscal {

	private ArrayList<String[]> nf;
	private ArrayList<String[]> nfe;
	private ArrayList<String[]> nfs;
	private Charset utf8 = StandardCharsets.UTF_8;

	public Fiscal(){}

	public Fiscal(String inputnf, String inputnfe , String inputnfs) throws IOException{
		Path caminhoinputnf = Paths.get(inputnf);
		Path caminhoinputnfe = Paths.get(inputnfe);
		Path caminhoinputnfs = Paths.get(inputnfs);
		
		nf = new ArrayList<>();
		nfe = new ArrayList<>();
		nfs = new ArrayList<>();
		
		try(BufferedReader ler = Files.newBufferedReader(caminhoinputnf, utf8)){
			String linha;
			while((linha = ler.readLine()) != null){
				nf.add(linha.split("\t"));
			}
		}
		
		try(BufferedReader lernfe = Files.newBufferedReader(caminhoinputnfe, utf8)){
			String linha;
			while((linha = lernfe.readLine()) != null){
				nfe.add(linha.split("\t"));
			}
		}
		
		try(BufferedReader lernfs = Files.newBufferedReader(caminhoinputnfs, utf8)){
			String linha;
			while((linha = lernfs.readLine()) != null){
				nfs.add(linha.split("\t"));
			}
		}
	}
	
	//CFOP 5102 6102 sem ICMS
	public void cfop5102Ou6102SemICMS(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfs.get(1)[1] + " - CFOP_5102_ou_6102_sem_ICMS.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio, utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfs){
				if(strings[51].equals("5102") || strings[51].equals("6102")){
					if(!strings[24].equals("S") && strings[77].equals("0")){
						for(String dado : strings){
							escreve.write(dado + ";");
						}
						escreve.write("\n");
					}
				}
			}
		}
	}
	
	//CFOP 5405 com destaque de ICMS
	public void cfop5405ComICMS(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfs.get(1)[1] + " - CFOP_5405_com_ICMS.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio, utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfs){
				if(strings[51].equals("5405") && !strings[77].equals("0") && !strings[24].equals("S")){
					for(String dado : strings){
						escreve.write(dado + ";");
					}
					escreve.write("\n");
				}
			}
		}
	}
	
	//CST de importação direta para entradas nos sites diferentes de 01, 15 e 28
	public void cstEntradaImportacaoSiteNaoImportador(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfe.get(1)[1] + " - Entrada_CST_de_importacao_site_nao_importador.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfe){
				if(!strings[1].equals("101") && !strings[1].equals("115") && !strings[1].equals("128")){
					if(strings[73].startsWith("1") || strings[73].startsWith("6")){
						for(String dado : strings){
							escreve.write(dado + ";");
						}
						escreve.write("\n");
					}
				}
			}
		}
	}
	
	//CST de importação direta para saidas nos sites diferentes de 01, 15 e 28
	public void cstSaidaImportacaoSiteNaoImportador(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfs.get(1)[1] + " - Saida_CST_de_importacao_site_nao_importador.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfs){
				if(!strings[1].equals("101") && !strings[1].equals("115") && !strings[1].equals("128")){
					if(strings[73].startsWith("1") || strings[73].startsWith("6") && !strings[24].equals("S")){
						for(String dado : strings){
							escreve.write(dado + ";");
						}
						escreve.write("\n");
					}
				}
			}
		}
	}
	
	//Entrada ICMS ST com saida ICMS normal
	public void entradaSTSaidaNormal(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nf.get(1)[0] + " - Entrada_ICMS_ST_Saida_ICMS_Normal.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			for(String coluna : nf.get(0)){
				escreve.write(coluna + ";");
			}
			escreve.write("\n");
			
			ArrayList<String> relacaoNCMST = new ArrayList<>();
			
			for (String[] strings : nf) {
				if(strings[25].startsWith("14") || strings[25].startsWith("24")){
					relacaoNCMST.add(strings[15]);					
				}
			}
			
			for (String[] strings2 : nf){
				if(strings2[25].startsWith("5") || strings2[25].equals("6108")){
					if(relacaoNCMST.contains(strings2[19])){
						if(!strings2[34].equals("0")){
							for(String dado : strings2){
								escreve.write(dado + ";");
							}
							escreve.write("\n");
						}
					}
				}
			}
		}
	}
	
	//Entradas com ICMS normal e saídas com ICMS ST
	public void entradaNormalSaidaST(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nf.get(1)[0] + " - Entrada_ICMS_Normal_Saida_ICMS_ST.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			for(String coluna : nf.get(0)){
				escreve.write(coluna + ";");
			}
			escreve.write("\n");
			
			ArrayList<String> relacaoNCMST = new ArrayList<>();
			
			for (String[] strings : nf) {
				if(strings[25].startsWith("11") || strings[25].startsWith("21")){
					relacaoNCMST.add(strings[15]);					
				}
			}
			
			for(String[] strings2 : nf){
				if(strings2[25].equals("5102") || strings2[25].equals("5405") || strings2[25].equals("6108")){
					if(relacaoNCMST.contains(strings2[19])){
						if(strings2[34].equals("0")){
							for(String dado : strings2){
								escreve.write(dado + ";");
							}
							escreve.write("\n");
						}
					}
				}
			}
		}
	}
	
	//verifica os CST e as aliquotas de ICMS
	public void cstVersusAliq(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfe.get(1)[1] + " - CST_versus_aliquota_ICMS.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfe){
				if(strings[51].startsWith("2")){
					if(strings[73].startsWith("0") || strings[73].startsWith("5") || strings[73].startsWith("6") || strings[73].startsWith("7")){
						if(strings[73].endsWith("00")){
							if(!strings[75].equals("7") && !strings[75].equals("12")){
								for(String dado : strings){
									escreve.write(dado + ";");
								}
								escreve.write("\n");
							}
						}
					}
					
					if(strings[73].startsWith("1") || strings[73].startsWith("2") || strings[73].startsWith("3") || strings[73].startsWith("8")){
						if(strings[73].endsWith("00")){
							if(!strings[75].equals("4")){
								for(String dado : strings){
									escreve.write(dado + ";");
								}
								escreve.write("\n");
							}
						}
					}
				}
			} 

			for(String[] strings : nfs){
				if(strings[51].startsWith("6") && !strings[51].startsWith("6108") && !strings[24].equals("S")){
					if(strings[73].startsWith("0") || strings[73].startsWith("5") || strings[73].startsWith("6") || strings[73].startsWith("7")){
						if(!strings[73].endsWith("40") && !strings[73].endsWith("41")&&
								!strings[73].endsWith("50") && !strings[73].endsWith("51") && !strings[73].endsWith("60")){
							if(!strings[75].equals("7") && !strings[75].equals("12")){
								for(String dado : strings){
									escreve.write(dado + ";");
								}
								escreve.write("\n");
							}
						}
					}
					
					if(strings[73].startsWith("1") || strings[73].startsWith("2") || strings[73].startsWith("3") || strings[73].startsWith("8")){
						if(!strings[73].endsWith("40") && !strings[73].endsWith("41") &&
								!strings[73].endsWith("50") && !strings[73].endsWith("51") && !strings[73].endsWith("60")){
							if(!strings[75].equals("4")){
								for(String dado : strings){
									escreve.write(dado + ";");
								}
								escreve.write("\n");
							}
						}
					}
					
					if(strings[73].endsWith("60") && !strings[75].equals("0")){
						for(String dado : strings){
							escreve.write(dado + ";");
						}
						escreve.write("\n");
					}
				}
			} 
		}
	}
	
	//redução de base de calculo para cliente ISENTO
	public void reducaoBCICMSParaIsento(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfs.get(1)[1] + " - Reducao_Base_ICMS_para_ISENTO.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfs){
				if(strings[73].endsWith("20") && strings[95].equals("ISENTO") && !strings[53].equals("84433910")
						&& !strings[24].equals("S")){
					for(String dado : strings){
						escreve.write(dado + ";");
					}
					escreve.write("\n");
				}
			}
		}
	}
	
	//CFOP 6102 para cliente ISENTO
	public void cfop6102ParaIsento(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfs.get(1)[1] + " - CFOP_6102_para_ISENTO.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfs){
				if(strings[51].equals("6102") && strings[95].equals("ISENTO") && !strings[24].equals("S")){
					for(String dado : strings){
						escreve.write(dado + ";");
					}
					escreve.write("\n");
				}
			}
		}
	}
	
	//CFOP 1403 1409 1411 2403 2409 com credito de ICMS 
	public void cfopICMSSTComCredito(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfe.get(1)[1] + " - Entrada_CFOP_ST_Com_Credito.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfe){
				if(strings[51].startsWith("14") || strings[51].startsWith("24") ){
					if(!strings[77].equals("0")){
						for(String dado : strings){
							escreve.write(dado + ";");
						}
						escreve.write("\n");
					}
				}
			}
		}
	}
	
	// CST de immportacao diferente de 1 ou 6
	public void cstImportacao(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfe.get(1)[1] + " - Entrada_importacao_com_CST_incorreta.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfe){
				if(strings[51].startsWith("3")){
					if(!strings[73].startsWith("1") && !strings[73].startsWith("6")){
						for(String dado : strings){
							escreve.write(dado + ";");
						}
						escreve.write("\n");
					}
				}
			}
		}
	}
	
	//Transferência de Imobilizado com ICMS CFOP 5552 e 6552
	public void transfAtivoComICMS(String caminho) throws IOException{
		Path relatorio = Paths.get(caminho + nfs.get(1)[1] + " - Transferencia_ativo_com_ICMS.csv");
		try(BufferedWriter escreve = Files.newBufferedWriter(relatorio,utf8)){
			this.cabecalho(escreve);
			for(String[] strings : nfs){
				if(strings[51].equals("5552") || strings[51].equals("6552") && !strings[24].equals("S")){
					if(!strings[77].equals("0")){
						for(String dado : strings){
							escreve.write(dado + ";");
						}
						escreve.write("\n");
					}
				}
			}
		}
	}
	
	//Venda de Maranhão e Goiás para clientes com inscrição estadual e ICMS sem redução de Base de cálculo
	public void vendasDeGoEMaIncritoSemRed(String caminho)throws IOException{
		if (nfs.get(1)[1].equals("121") || nfs.get(1)[1].equals("123")) {
			Path relatorio = Paths.get(caminho + nfs.get(1)[1]
						+ " - Venda_para_inscrito_sem_redução_de_ICMS.csv");
			try (BufferedWriter escreve = Files.newBufferedWriter(
						relatorio, utf8)) {
				this.cabecalho(escreve);
				for (String[] strings : nfs) {
					if (strings[51].equals("5102") && !strings[24].equals("S")) {
						if (!strings[5].equals("CF") && !strings[95].equals("ISENTO")
									&& !strings[73].endsWith("20")) {
							for (String dado : strings) {
								escreve.write(dado + ";");
							}
							escreve.write("\n");
						}
					}
				}
			}
		}
	}
	
	//verifica se a aliquota de icms de saida de importação direta de manaus é diferente de 7%
	public void saidaInternaManausProdImp(String caminho) throws IOException{
		if (nfs.get(1)[1].equals("115")){
			Path relatorio = Paths.get(caminho + nfs.get(1)[1]
					+ " - ICMS_venda_interna_Manaus_Produto_Importado.csv");
			try (BufferedWriter escreve = Files.newBufferedWriter(
					relatorio, utf8)){
				this.cabecalho(escreve);
				
				ArrayList<String> listaImportados = new ArrayList<>();
				
				for(String[] strings : nfe){
					if(strings[51].startsWith("3")){
						listaImportados.add(strings[47]);
					}
				}
				
				for (String[] strings2 : nfs){
					if(strings2[51].equals("5102") || strings2[51].equals("5910") && !strings2[24].equals("S")){
						if(listaImportados.contains(strings2[47]) && !strings2[75].equals("7")){
							for (String dado : strings2) {
								escreve.write(dado + ";");
							}
							escreve.write("\n");
						}
					}
				}
			}
		}
	}
	
	
	
	//gera cabecalho para NFE e NFS
	private void cabecalho(BufferedWriter bw) throws IOException{
		for(String s : nfs.get(0)){
			bw.write(s + ";");
		}
		bw.write("\n");
	}
	
	//gera todos relatorios
	public void todos(String caminho) throws IOException{
		this.cfop5102Ou6102SemICMS(caminho);
		this.cfop5405ComICMS(caminho);
		this.cfop6102ParaIsento(caminho);
		this.cfopICMSSTComCredito(caminho);
		this.cstEntradaImportacaoSiteNaoImportador(caminho);
		this.cstImportacao(caminho);
		this.cstSaidaImportacaoSiteNaoImportador(caminho);
		this.cstVersusAliq(caminho);
		this.entradaNormalSaidaST(caminho);
		this.entradaSTSaidaNormal(caminho);
		this.reducaoBCICMSParaIsento(caminho);
		this.transfAtivoComICMS(caminho);
		this.vendasDeGoEMaIncritoSemRed(caminho);
		this.saidaInternaManausProdImp(caminho);
	}
}