package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Transacao;
import model.dao.TransacaoDao;

/**
 * 
 * @author Romário
 *
 *Janela principal do sistema.
 */
public class MainFrame extends JFrame{

	/** Tabela que aprensentara os dados. */
	private JTable table;

	private TransacaoDao trandao = TransacaoDao.getInstancia();
	
	/**
	 * Construtor.
	 */
	public MainFrame() {
		build();
	}

	/**
	 * Constrói a janela.
	 */
	private void build() {
		setLayout(new GridBagLayout());
		setTitle("Gestão de Pousada");
		buildComponents();
		setSize(1100, 600);
		setMinimumSize(new Dimension(400, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	/**
	 * Constrói os componentes da janela.
	 */
	private void buildComponents() {
		buildMainPanel();
		buildButtonPanel();
	}

	/**
	 * Constrói o painel de botões.
	 */
	private void buildButtonPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		JButton newButton = new JButton("Novo");
		panel.add(newButton, new GBC(0, 0).horizontal());
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TransacaoDialog(MainFrame.this);
			}
		});

		JButton editButton = new JButton("Alterar");
		panel.add(editButton, new GBC(0, 1).horizontal());
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				GestaoTableModel model = (GestaoTableModel) table.getModel();
				Transacao tran = model.getRow(selectedRow);
				new TransacaoDialog(MainFrame.this, tran, table.getSelectedRow());
			}
		});

		JButton deleteButton = new JButton("Excluir");
		panel.add(deleteButton, new GBC(0, 2).horizontal());
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				GestaoTableModel model = (GestaoTableModel) table.getModel();
				Transacao tran = model.getRow(selectedRow);
				excluiTransacao(tran, table.getSelectedRow());
			}
		});
		
		JButton calculosButton = new JButton("Calcular");
		panel.add(calculosButton, new GBC(0, 3).horizontal());
		calculosButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CalculoDialog(MainFrame.this);
			}
		});

		add(panel, new GBC(1, 0));
	}

	/**
	 * Constrói o painel principal.
	 */
	private void buildMainPanel() {
		JPanel panel = new JPanel(new GridBagLayout());

		String[] items = { "check-in", "check-out" };
		JComboBox<String> comboBox = new JComboBox<>(items);
		panel.add(comboBox, new GBC(0, 0));

		JTextField searchField = new JTextField(10);
		panel.add(searchField, new GBC(1, 0).horizontal());

		buildTable(panel);

		add(panel, new GBC(0, 0).both().insets(10, 5, 5, 0));
	}

	/**
	 * Constrói a tabela.
	 * 
	 * @param panel
	 *            Painel principal.
	 */
	private void buildTable(JPanel panel) {
		table = new JTable();
		table.setModel(new GestaoTableModel());
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, new GBC(0, 1).gridwh(2, 1).both());
	}

	/**
	 * Atualiza os dados da transação.
	 * 
	 * @param transacao
	 * @param selectedRow
	 */
	public void updateTransacao(Transacao t, int selectedRow) {
		GestaoTableModel model = (GestaoTableModel) table.getModel();
		trandao.update(t);
		model.updateTransacao(t, selectedRow);
	}

	/**
	 * Adiciona uma transação.
	 * 
	 * @param transacao
	 */
	public void addTransacao(Transacao t) {	
		GestaoTableModel model = (GestaoTableModel) table.getModel();
		trandao.toPersist(t);
		model.addTransacao(t);
	}
	
	/**
	 * Exclui uma transação
	 * 
	 * @param tran
	 * @param row
	 */
	private void excluiTransacao(Transacao t, int row) {
		GestaoTableModel model = (GestaoTableModel) table.getModel();
		trandao.remove(t);
		model.removeTransacao(row);
	}
}
