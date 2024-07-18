/* *******************************************************************************************************
 * Código referente a interface gráfica e seus eventos, do programa de validações fiscais.               *
 * Versão 1.0                                                                                            *
 * Utiliza arquivos txt gerados a partir do programa Mastersaf Smart, analíticos, codificados em UTF-8,  * 
 * com seus dados separados por tabulações:                                                              *
 * - NF relação notas - Entrada e Saída                                                                  *
 * - NF planilha notas - Entradas                                                                        *
 * - NF planilha notas - Saídas                                                                          *
 * *******************************************************************************************************/

package analise;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIAnalise extends JFrame implements ActionListener{

	JFileChooser fc;
	JFileChooser fcSaida;
	JButton botaoNF;
	JTextField caminhoNF;
	JButton botaoNFE;
	JButton botaoNFS;
	JButton botaoSaida;
	JTextField caminhoNFE;
	JTextField caminhoNFS;
	JTextField caminhoSaida;
	JButton ok;
	JButton cancelar;
	JCheckBox todos;
	JCheckBox cfop5102Ou6102SemICMS;
	JCheckBox cfop5405ComICMS;
	JCheckBox cstEntradaImportacaoSiteNaoImportador;
	JCheckBox cstSaidaImportacaoSiteNaoImportador;
	JCheckBox entradaSTSaidaNormal;
	JCheckBox entradaNormalSaidaST;
	JCheckBox cstVersusAliq;
	JCheckBox reducaoBCICMSParaIsento;
	JCheckBox cfop6102ParaIsento;
	JCheckBox cfopICMSSTComCredito;
	JCheckBox cstImportacao;
	JCheckBox transfAtivoComICMS;
	JCheckBox vendasDeGoEMaIncritoSemRed;
	JCheckBox saidaInternaManausProdImp;
	Fiscal objFiscal;

	public GUIAnalise(){
		super("Validações");
		Container containerPrincipal = getContentPane();
		containerPrincipal.setLayout(new BorderLayout());
		Container IO = new JPanel();
		IO.setLayout(new GridLayout(4,3));
		JLabel nf = new JLabel("NF relação Notas ES");
		JLabel nfe = new JLabel("NF planilha produtos Entrada");
		JLabel nfs = new JLabel("NF planilha produtos saída");
		JLabel saida = new JLabel("Salvar relatórios em:");
		caminhoNF = new JTextField();
		caminhoNFE = new JTextField();
		caminhoNFS = new JTextField();
		caminhoSaida = new JTextField();
		Container b1 = new JPanel();
		b1.setLayout(new FlowLayout());
		Container b2 = new JPanel();
		b1.setLayout(new FlowLayout());
		Container b3 = new JPanel();
		b1.setLayout(new FlowLayout());
		Container b4 = new JPanel();
		b1.setLayout(new FlowLayout());
		botaoNF = new JButton("Procurar");
		botaoNF.addActionListener(this);
		botaoNFE = new JButton("Procurar");
		botaoNFE.addActionListener(this);
		botaoNFS = new JButton("Procurar");
		botaoNFS.addActionListener(this);
		botaoSaida = new JButton("Procurar");
		botaoSaida.addActionListener(this);
		b1.add(botaoNF);
		b2.add(botaoNFE);
		b3.add(botaoNFS);
		b4.add(botaoSaida);
		IO.add(nf);
		IO.add(caminhoNF);
		IO.add(b1);
		IO.add(nfe);
		IO.add(caminhoNFE);
		IO.add(b2);
		IO.add(nfs);
		IO.add(caminhoNFS);
		IO.add(b3);
		IO.add(saida);
		IO.add(caminhoSaida);
		IO.add(b4);
		Container opcoes = new JPanel();
		opcoes.setLayout(new BorderLayout());
		Container check = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		check.setLayout(fl);
		Container okCancela = new JPanel();
		okCancela.setLayout(new FlowLayout());
		todos = new JCheckBox("Todos");
		cfop5102Ou6102SemICMS = new JCheckBox("CFOP 5102 ou 6102 sem ICMS");
		cfop5405ComICMS = new JCheckBox("CFOP 5405 com ICMS");
		cstEntradaImportacaoSiteNaoImportador = new JCheckBox("Entrada CST de importação para site não importador");
		cstSaidaImportacaoSiteNaoImportador = new JCheckBox("Saída CST de importação para site não importador");
		entradaSTSaidaNormal = new JCheckBox("Entrada ICMS ST com saída ICMS normal");
		entradaNormalSaidaST = new JCheckBox("Entrada ICMS normal com saída ICMS ST");
		cstVersusAliq = new JCheckBox("CST versus alíquotas de ICMS");
		reducaoBCICMSParaIsento = new JCheckBox("Redução de Base de cálculo de ICMS para cliente ISENTO");
		cfop6102ParaIsento = new JCheckBox("CFOP 6102 para cliente ISENTO");
		cfopICMSSTComCredito = new JCheckBox("Entrada CFOP ICMS ST com crédito");
		cstImportacao = new JCheckBox("Entrada importação com CST incorreta");
		transfAtivoComICMS = new JCheckBox("Saída de transferência de ativo imobilizado com ICMS");
		vendasDeGoEMaIncritoSemRed = new JCheckBox("Vendas site 21 e 23 sem redução de ICMS");
		saidaInternaManausProdImp = new JCheckBox("Saída de Manaus de importação direta com ICMS incorreto");
		check.add(todos);
		check.add(cfop5102Ou6102SemICMS);
		check.add(cfop5405ComICMS);
		check.add(cstEntradaImportacaoSiteNaoImportador);
		check.add(cstSaidaImportacaoSiteNaoImportador);
		check.add(entradaSTSaidaNormal);
		check.add(entradaNormalSaidaST);
		check.add(cstVersusAliq);
		check.add(reducaoBCICMSParaIsento);
		check.add(cfop6102ParaIsento);
		check.add(cfopICMSSTComCredito);
		check.add(cstImportacao);
		check.add(transfAtivoComICMS);
		check.add(vendasDeGoEMaIncritoSemRed);
		check.add(saidaInternaManausProdImp);
		ok = new JButton("Ok");
		ok.addActionListener(this);
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(this);
		okCancela.add(ok);
		okCancela.add(cancelar);
		opcoes.add(BorderLayout.CENTER, check);
		opcoes.add(BorderLayout.SOUTH, okCancela);
		containerPrincipal.add(BorderLayout.NORTH, IO);
		containerPrincipal.add(BorderLayout.CENTER, opcoes);

		setSize(1000,400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == botaoNF){
			fc = new JFileChooser();
			int v = fc.showOpenDialog(GUIAnalise.this);
			
			if(v == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				String s = file.getPath();
				caminhoNF.setText(s);
			}
		}else if(arg0.getSource() == botaoNFE){
			fc = new JFileChooser();
			int v = fc.showOpenDialog(GUIAnalise.this);
			
			if(v == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				String s = file.getPath();
				caminhoNFE.setText(s);
			}
		}else if(arg0.getSource() == botaoNFS){
			fc = new JFileChooser();
			int v = fc.showOpenDialog(GUIAnalise.this);
			
			if(v == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				String s = file.getPath();
				caminhoNFS.setText(s);
			}
		}else if(arg0.getSource() == botaoSaida){
			fcSaida = new JFileChooser();
			fcSaida.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int v = fcSaida.showOpenDialog(GUIAnalise.this);
			
			if(v == JFileChooser.APPROVE_OPTION){
				File file = fcSaida.getSelectedFile();
				String s = file.getPath();
				caminhoSaida.setText(s + File.separator);
			}
		}else if(arg0.getSource() == ok){
			try {
				objFiscal = new Fiscal(caminhoNF.getText(),caminhoNFE.getText(),caminhoNFS.getText());
				String saida = caminhoSaida.getText();

				if(todos.isSelected()){
					objFiscal.todos(saida);
				}else{
					if(cfop5102Ou6102SemICMS.isSelected()){
						objFiscal.cfop5102Ou6102SemICMS(saida);
					}
					if(cfop5405ComICMS.isSelected()){
						objFiscal.cfop5405ComICMS(saida);
					}
					if(cstEntradaImportacaoSiteNaoImportador.isSelected()){
						objFiscal.cstEntradaImportacaoSiteNaoImportador(saida);
					}
					if(cstSaidaImportacaoSiteNaoImportador.isSelected()){
						objFiscal.cstSaidaImportacaoSiteNaoImportador(saida);
					}
					if(entradaSTSaidaNormal.isSelected()){
						objFiscal.entradaSTSaidaNormal(saida);
					}
					if(entradaNormalSaidaST.isSelected()){
						objFiscal.entradaNormalSaidaST(saida);
					}
					if(cstVersusAliq.isSelected()){
						objFiscal.cstVersusAliq(saida);
					}
					if(reducaoBCICMSParaIsento.isSelected()){
						objFiscal.reducaoBCICMSParaIsento(saida);
					}
					if(cfop6102ParaIsento.isSelected()){
						objFiscal.cfop6102ParaIsento(saida);
					}
					if(cfopICMSSTComCredito.isSelected()){
						objFiscal.cfopICMSSTComCredito(saida);
					}
					if(cstImportacao.isSelected()){
						objFiscal.cstImportacao(saida);
					}
					if(transfAtivoComICMS.isSelected()){
						objFiscal.transfAtivoComICMS(saida);
					}
					if(vendasDeGoEMaIncritoSemRed.isSelected()){
						objFiscal.vendasDeGoEMaIncritoSemRed(saida);
					}
					if(saidaInternaManausProdImp.isSelected()){
						objFiscal.saidaInternaManausProdImp(saida);
					}
				}

			} catch (IOException e) {
				JOptionPane.showMessageDialog(GUIAnalise.this, 
						"Houve algum problema na importação de dados. " +
						"Verifique se os caminhos estão corretos.",
						"Erro de importação",JOptionPane.ERROR_MESSAGE);
			}
		}else if(arg0.getSource() == cancelar){
			this.dispose();
		}
	}

	public static void main(String[] args){
		new GUIAnalise();
	}
}
