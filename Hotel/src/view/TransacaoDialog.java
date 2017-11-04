package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Transacao;

public class TransacaoDialog extends JDialog{

	/** Janela principal do sistema. */
	private MainFrame mainFrame;
	/** Campo do nome do cliente. */
	private JTextField clientField;
	/** Campo da quantidade de hospedes. */
	private JTextField quantHospedesField;
	/** Campo da data do check-in. */
	private JTextField checkInField;
	/** Campo da data do check-out. */
	private JTextField checkOutField;
	/** Campo da quantidade de noites */
	private JTextField quantNoitesField;
	/** Campo do tipo UH*/
	private JTextField tipoUhField;
	/** Campo do valor da diaria */
	private JTextField valDiariaField;
	/** Campo do valor UH*/
	private JTextField valUhField;
	/** Campo do valor extra. */
	private JTextField valoExtraField;
	/** Cliente sendo editado. Null quando é adição de novo cliente. */
	private Transacao tran;
	/** Linha da tabela selecionada pelo usuário para edição. */
	private int selectedRow;
	
	/**
	 * Construtor.
	 * 
	 * @param mainframe
	 * 				Janela principal.
	 */
	public TransacaoDialog(MainFrame mainframe) {
		this(mainframe, null, 0);
	}

	/**
	 * Construtor.
	 * 
	 * @param mainframe
	 * 				Janela Principal.
	 * @param tran 
	 * 			Transacao
	 * @param selectRow
	 * 				Linha selecionada. 
	 */
	public TransacaoDialog(MainFrame mainframe, Transacao tran, int selectRow) {
		this.mainFrame = mainframe;
		this.tran = tran;
		this.selectedRow = selectRow;
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
		setTitle("Adicionar Transação");
		setLocationRelativeTo(mainFrame);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

	/**
	 * Constrói os componentes da janela.
	 */
	private void buildComponents() {
		JLabel jLabel1 = new JLabel("Nome Cliente:");
		add(jLabel1, new GBC(0, 0).left().insets(10, 10, 0, 0));
		clientField = new JTextField(20);
		add(clientField, new GBC(1, 0).insets(10, 10, 0, 0));
		
		JLabel jLabe2 = new JLabel("Quantidade de Hóspedes:");
		add(jLabe2, new GBC(0, 1).left().insets(10, 10, 0, 0));
		quantHospedesField = new JTextField(20);
		add(quantHospedesField, new GBC(1, 1).insets(10, 10, 0, 0));
		
		JLabel jLabe3 = new JLabel("Check-in:");
		add(jLabe3, new GBC(0, 2).left().insets(10, 10, 0, 0));
		checkInField = new JTextField(20);
		add(checkInField, new GBC(1, 2).insets(10, 10, 0, 0));
		
		JLabel jLabe4 = new JLabel("Check-out:");
		add(jLabe4, new GBC(0, 3).left().insets(10, 10, 0, 0));
		checkOutField = new JTextField(20);
		add(checkOutField, new GBC(1, 3).insets(10, 10, 0, 0));
		
		JLabel jLabel5 = new JLabel("Quantidade de Noites:");
		add(jLabel5, new GBC(0, 4).left().insets(10, 10, 0, 0));
		quantNoitesField = new JTextField(20);
		add(quantNoitesField, new GBC(1, 4).insets(10, 10, 0, 0));
		
		JLabel jLabel6 = new JLabel("Tipo UH:");
		add(jLabel6, new GBC(0, 5).left().insets(10, 10, 0, 0));
		tipoUhField = new JTextField(20);
		add(tipoUhField, new GBC(1, 5).insets(10, 10, 0, 0));
		
		JLabel jLabel7 = new JLabel("Valor da Diária:");
		add(jLabel7, new GBC(0, 6).left().insets(10, 10, 0, 0));
		valDiariaField = new JTextField(20);
		add(valDiariaField, new GBC(1, 6).insets(10, 10, 0, 0));
		
		JLabel jLabel8 = new JLabel("R$/UH:");
		add(jLabel8, new GBC(0, 7).left().insets(10, 10, 0, 0));
		valUhField = new JTextField(20);
		add(valUhField, new GBC(1, 7).insets(10, 10, 0, 0));
		
		JLabel jLabel9 = new JLabel("Gastos Extras:");
		add(jLabel9, new GBC(0, 8).left().insets(10, 10, 0, 0));
		valoExtraField = new JTextField(20);
		add(valoExtraField, new GBC(1, 8).insets(10, 10, 0, 0));
	
		JButton saveButton = new JButton("Salvar");
		add(saveButton, new GBC(1, 9).right().insets(10, 10, 0, 0));
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(tran != null) {
					mainFrame.updateTransacao(getTransacao(), selectedRow);
				} else {
					mainFrame.addTransacao(getTransacao());
				}
				TransacaoDialog.this.dispose();
			}
		});
		
		if(tran != null) {
			setFields();
		}
	}
	
	/**
	 * Configura os campos com os dados da transacao.
	 */
	private void setFields() {
		clientField.setText(tran.getCliente());
		quantHospedesField.setText(Integer.toString(tran.getQuantHospedes()));
		checkInField.setText(tran.getCheckIn());
		checkOutField.setText(tran.getCheckOut());
		quantNoitesField.setText(Integer.toString(tran.getQuantNoites()));
		tipoUhField.setText(tran.getTipoUh());
		valDiariaField.setText(Double.toString(tran.getValDiaria()));
		valUhField.setText(Double.toString(tran.getValUh()));
		valoExtraField.setText(Double.toString(tran.getValoExtra()));
	}

	/**
	 * @return A transacao com as informações inseridas pelo usuário.
	 */
	private Transacao getTransacao() {
		if (tran != null) {
			return new Transacao(tran.getIdTransacao() ,clientField.getText(), quantHospedesField.getText(), checkInField.getText(), checkOutField.getText(), quantNoitesField.getText(), tipoUhField.getText(), valDiariaField.getText(), valUhField.getText(), valoExtraField.getText());
		} else {
			return new Transacao(clientField.getText(), quantHospedesField.getText(), checkInField.getText(), checkOutField.getText(), quantNoitesField.getText(), tipoUhField.getText(), valDiariaField.getText(), valUhField.getText(), valoExtraField.getText());
		}	
	}	
}
