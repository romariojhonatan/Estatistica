package control;

import model.Transacao;
import model.Utility;
import model.dao.TransacaoDao;
import view.CalculoDialog;
import view.MainFrame;

public class MainApp {

	public static void main(String[] args) {
//		TransacaoDao t = TransacaoDao.getInstancia();
//		double r = t.mediaPeriodo(Utility.stringToDate("01/01/2017"), Utility.stringToDate("28/02/2017"), 1);
//		System.out.println(r);
		//new TransacaoDialog(null);
//		new CalculoDialog(null);
		new MainFrame();
	}
}