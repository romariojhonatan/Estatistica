package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.dao.TransacaoDao;

public class CalculoDialog extends JDialog{

	/** Janela principal do sistema. */
	private MainFrame mainFrame;
	
	private TransacaoDao tran = TransacaoDao.getInstancia();
	
	private String[] calculos = {  "Selecione o Cálculo", "Média de Reservas", "Média de Noites", 
			"Média de Diárias", "Média de Gastos Extras","Média de Gasto Total", 
			"Média de Pessoas", "Média Ocupação"};
	
	private String[] mes = { "Selecione Mês", "Janeiro", "Fevereiro", "Março", "Abril", 
			"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", 
			"Novembro", "Dezembro"};
	
	private String[] tipoDeCalculos = { "Selecione Tipo", "Mensal", "Anual", "Periodo"};
	
	private String[] ano = { "Selecione Ano", "2017", "2018"};
	
	private JComboBox calculoComboBox= new JComboBox(calculos);
	
	private JComboBox tipoComboBox= new JComboBox(tipoDeCalculos);
	
	private JComboBox deComboBox = new JComboBox(mes);
	
	private JComboBox ateComboBox = new JComboBox(mes);
	
	private JComboBox anoComboBox = new JComboBox(ano);
	
	private JTextField resultTextField;
	
	private JButton calcButton;
	
	/**
	 * Construtor.
	 * 
	 * @param mainframe
	 * 				Janela principal.
	 */
	public CalculoDialog(MainFrame mainFrame) {
		build();
	}

	/**
	 * Constroi a Janela.
	 */
	private void build() {
		setLayout(new GridBagLayout());
		buildComponents();
		pack();
		setResizable(false);
		setTitle("Cálculos");
		setLocationRelativeTo(mainFrame);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

	/**
	 * Constrói os componentes da janela.
	 */
	private void buildComponents() {
		primeiraLinha();
		segundaLinha();
		terceiraLinha();
	}

	private void primeiraLinha() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel jLabel1 = new JLabel("Escolha o Tipo de Cálculo:");
		panel.add(jLabel1, new GBC(0, 0));
		panel.add(calculoComboBox, new GBC(1, 0));
		
		JLabel jLabel2 = new JLabel("Tipo de Cálculo:");
		panel.add(jLabel2, new GBC(2, 0));
		panel.add(tipoComboBox, new GBC(3, 0));
		tipoComboBox.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String)tipoComboBox.getSelectedItem();
				
				deComboBox.setEnabled(false);
				ateComboBox.setEnabled(false);
				anoComboBox.setEnabled(false);
				
				if (item.equals("Mensal")) {
					deComboBox.setEnabled(true);
					ateComboBox.setEnabled(false);
					anoComboBox.setEnabled(true);
					
				} if (item.equals("Anual")) {
					deComboBox.setEnabled(false);
					ateComboBox.setEnabled(false);
					anoComboBox.setEnabled(true);
				
				} if (item.equals("Periodo")) {
					deComboBox.setEnabled(true);
					ateComboBox.setEnabled(true);
					anoComboBox.setEnabled(true);	
				
				}
			}
		});
		
		add(panel, new GBC(0, 0));
	}

	private void segundaLinha() {
		JPanel panel = new JPanel(new GridBagLayout());
	
		deComboBox.setEnabled(false);
		ateComboBox.setEnabled(false);
		anoComboBox.setEnabled(false);
		
		JLabel jLabel1 = new JLabel("De:");
		panel.add(jLabel1, new GBC(0, 1));
		panel.add(deComboBox, new GBC(1, 1));
		
		JLabel jLabel2 = new JLabel("Até:");
		panel.add(jLabel2, new GBC(2, 1));
		panel.add(ateComboBox, new GBC(3, 1));
		
		JLabel jLabel3 = new JLabel("Ano:");
		panel.add(jLabel3, new GBC(4, 1));
		panel.add(anoComboBox, new GBC(5, 1));
		
		add(panel, new GBC(0, 1));
	}
	
	private void terceiraLinha() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		resultTextField = new JTextField(10);
		resultTextField.setEditable(false);
		panel.add(resultTextField, new GBC(0, 2));
		
		calcButton = new JButton("Calcular");
		panel.add(calcButton, new GBC(1, 2));
		calcButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String calculo = (String)calculoComboBox.getSelectedItem();
				String tipo = (String)tipoComboBox.getSelectedItem();
				String de = (String)deComboBox.getSelectedItem();
				String ate = (String)ateComboBox.getSelectedItem();
				String ano = (String)anoComboBox.getSelectedItem();		
				
				boolean valido = validacao(calculo,tipo,de,ate,ano);
				
				double resutado = 0;
				
				if (valido) {
					Date deDate = model.Utility.stringToDate("01/01/2017");
					Date ateDate = model.Utility.stringToDate("01/01/2017");
					Date anoDate = model.Utility.stringToDate("01/01/2017");
					switch (de) {
					case "Janeiro":
						deDate = model.Utility.stringToDate("01/01/2017");
						break;
					case "Fevereiro":
						deDate = model.Utility.stringToDate("01/02/2017");
						break;
					case "Março":
						deDate = model.Utility.stringToDate("01/03/2017");
						break;
					case "Abril":
						deDate = model.Utility.stringToDate("01/04/2017");
						break;
					case "Maio":
						deDate = model.Utility.stringToDate("01/05/2017");
						break;
					case "Junho":
						deDate = model.Utility.stringToDate("01/06/2017");
						break;
					case "Julho":
						deDate = model.Utility.stringToDate("01/07/2017");
						break;
					case "Agosto":
						deDate = model.Utility.stringToDate("01/08/2017");
						break;
					case "Setembro":
						deDate = model.Utility.stringToDate("01/09/2017");
						break;
					case "Outubro":
						deDate = model.Utility.stringToDate("01/10/2017");
						break;
					case "Novembro":
						deDate = model.Utility.stringToDate("01/11/2017");
						break;
					case "Dezembro":
						deDate = model.Utility.stringToDate("01/12/2017");
						break;
					}
					
					switch (ate) {
					case "Janeiro":
						ateDate = model.Utility.stringToDate("31/01/2017");
						break;
					case "Fevereiro":
						ateDate = model.Utility.stringToDate("28/02/2017");
						break;
					case "Março":
						ateDate = model.Utility.stringToDate("31/03/2017");
						break;
					case "Abril":
						ateDate = model.Utility.stringToDate("30/04/2017");
						break;
					case "Maio":
						ateDate = model.Utility.stringToDate("31/05/2017");
						break;
					case "Junho":
						ateDate = model.Utility.stringToDate("30/06/2017");
						break;
					case "Julho":
						ateDate = model.Utility.stringToDate("31/07/2017");
						break;
					case "Agosto":
						ateDate = model.Utility.stringToDate("31/08/2017");
						break;
					case "Setembro":
						ateDate = model.Utility.stringToDate("30/09/2017");
						break;
					case "Outubro":
						ateDate = model.Utility.stringToDate("31/10/2017");
						break;
					case "Novembro":
						ateDate = model.Utility.stringToDate("30/11/2017");
						break;
					case "Dezembro":
						ateDate = model.Utility.stringToDate("31/12/2017");
						break;
					}
					
					switch (ano) {
					case "2017":
						deDate.setYear(117);
						ateDate.setYear(117);
						anoDate.setYear(117);
						break;
					case "2018":
						deDate.setYear(118);
						ateDate.setYear(118);
						anoDate.setYear(118);
						break;
					}
					
					switch (calculo) {
					case "Média de Reservas":
						switch (tipo) {
						case "Mensal":
							resutado = tran.mediaMensal(deDate,1);
							break;
						case "Anual":
							resutado = tran.mediaAnual(anoDate,1);
							break;
						case "Periodo":
							resutado = tran.mediaPeriodo(deDate,ateDate,1);
							break;
						}
						break;
					case "Média de Noites":
						switch (tipo) {
						case "Mensal":
							resutado = tran.mediaMensal(deDate,2);
							break;
						case "Anual":
							resutado = tran.mediaAnual(anoDate,2);
							break;
						case "Periodo":
							resutado = tran.mediaPeriodo(deDate,ateDate,2);
							break;
						}
						break;
					case "Média de Diárias":
						switch (tipo) {
						case "Mensal":
							resutado = tran.mediaMensal(deDate,3);
							break;
						case "Anual":
							resutado = tran.mediaAnual(anoDate,3);
							break;
						case "Periodo":
							resutado = tran.mediaPeriodo(deDate,ateDate,3);
							break;
						}
						break;
					case "Média de Gastos Extras":
						switch (tipo) {
						case "Mensal":
							resutado = tran.mediaMensal(deDate,4);
							break;
						case "Anual":
							resutado = tran.mediaAnual(anoDate,4);
							break;
						case "Periodo":
							resutado = tran.mediaPeriodo(deDate,ateDate,4);
							break;
						}
						break;
					case "Média de Gasto Total":
						switch (tipo) {
						case "Mensal":
							resutado = tran.mediaMensal(deDate,5);
							break;
						case "Anual":
							resutado = tran.mediaAnual(anoDate,5);
							break;
						case "Periodo":
							resutado = tran.mediaPeriodo(deDate,ateDate,5);
							break;
						}
						break;
					case "Média de Pessoas":
						switch (tipo) {
						case "Mensal":
							resutado = tran.mediaMensal(deDate,6);
							break;
						case "Anual":
							resutado = tran.mediaAnual(anoDate,6);
							break;
						case "Periodo":
							resutado = tran.mediaPeriodo(deDate,ateDate,6);
							break;
						}
						break;
					case "Média Ocupapação":
						switch (tipo) {
						case "Mensal":
							resutado = tran.mediaMensal(deDate,7);
							break;
						case "Anual":
							resutado = tran.mediaAnual(anoDate,7);
							break;
						case "Periodo":
							resutado = tran.mediaPeriodo(deDate,ateDate,7);
							break;
						}
						break;
					}
					
					if (resutado == -1) {
						JOptionPane.showMessageDialog(null, "Não foi encontrado nenhuma reserva nesse periodo");
					} else {
						resultTextField.setText(Double.toString(resutado));
					}
					
				}
			}

			private boolean validacao(String calculo, String tipo, String de, String ate, String ano) {
				
				if (calculo.equals("Selecione o Cálculo")) {
					JOptionPane.showMessageDialog(null, "Tem que Selecionar um Cálculo", "Atenção", JOptionPane.ERROR_MESSAGE);
					return false;
				} else {
					if (tipo.equals("Selecione Tipo")) {
						JOptionPane.showMessageDialog(null, "Tem que Selecionar um Tipo", "Atenção", JOptionPane.ERROR_MESSAGE);
						return false;
					}else {
						if (tipo.equals("Mensal")) {
							if (de.equals("Selecione Mês")) {
								JOptionPane.showMessageDialog(null, "Tem que Selecionar um Mês", "Atenção", JOptionPane.ERROR_MESSAGE);
								return false;
							}else {
								if (ano.equals("Selecione Ano")) {
									JOptionPane.showMessageDialog(null, "Tem que Selecionar um Ano", "Atenção", JOptionPane.ERROR_MESSAGE);
									return false;
								} else {
									return true;
								}
							}
						} else {
							if (tipo.equals("Anual")) {
								if (ano.equals("Selecione Ano")) {
									JOptionPane.showMessageDialog(null, "Tem que Selecionar um Ano", "Atenção", JOptionPane.ERROR_MESSAGE);
									return false;
								} else {
									return true;
								}
							} else {
								if (tipo.equals("Periodo")) {
									if (de.equals("Selecione Mês")) {
										JOptionPane.showMessageDialog(null, "Tem que Selecionar um Mês", "Atenção", JOptionPane.ERROR_MESSAGE);
										return false;
									} else {
										if (ate.equals("Selecione Mês")) {
											JOptionPane.showMessageDialog(null, "Tem que Selecionar um Mês", "Atenção", JOptionPane.ERROR_MESSAGE);
											return false;
										} else {
											if (ano.equals("Selecione Ano")) {
												JOptionPane.showMessageDialog(null, "Tem que Selecionar um Ano", "Atenção", JOptionPane.ERROR_MESSAGE);
												return false;
											} else {
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
				return false;
			}
		});
		
		add(panel, new GBC(0, 2));
	}

}
