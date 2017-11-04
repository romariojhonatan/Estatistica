package model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Transacao;
import model.Utility;

public class TransacaoDao extends DaoGenerico<Transacao>{

	private EntityManager entity = getEntityManager();
	
	private static TransacaoDao instancia;

	private TransacaoDao() {
		
	}

	public static TransacaoDao getInstancia() {

		if (instancia == null) {
			instancia = new TransacaoDao();
			return instancia;
		} else {
			return instancia;
		}
	}
	
	public List<Transacao> searchAll() {
		return entity.createQuery("FROM Transacao t").getResultList();
	}

	public double mediaMensal(Date deDate, int i) {
		int mes = deDate.getMonth();
		int ano = deDate.getYear();
		Date ateDate = null;	
		
		switch (mes) {
		case 0:
			ateDate = Utility.stringToDate("31/01/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 1:
			if (ano % 4 == 0) {
				ateDate = Utility.stringToDate("29/02/2017");
				ateDate.setYear(ano);
				return mediaPeriodo(deDate, ateDate, i);
			} else {
				ateDate = Utility.stringToDate("28/02/2017");
				ateDate.setYear(ano);
				return mediaPeriodo(deDate, ateDate, i);
			}
		case 2:
			ateDate = Utility.stringToDate("31/03/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 3:
			ateDate = Utility.stringToDate("30/04/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 4:
			ateDate = Utility.stringToDate("31/05/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 5:
			ateDate = Utility.stringToDate("30/06/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 6:
			ateDate = Utility.stringToDate("31/07/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 7:
			ateDate = Utility.stringToDate("31/08/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 8:
			ateDate = Utility.stringToDate("31/09/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 9:
			ateDate = Utility.stringToDate("31/10/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 10:
			ateDate = Utility.stringToDate("30/11/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		case 11:
			ateDate = Utility.stringToDate("31/12/2017");
			ateDate.setYear(ano);
			return mediaPeriodo(deDate, ateDate, i);
		}
		return -1;
	}

	public double mediaAnual(Date anoDate, int i) {
		int ano = anoDate.getYear();
		Date ateDate = Utility.stringToDate("31/12/2017");
		ateDate.setYear(ano);
		
		return mediaPeriodo(anoDate, ateDate, i);
	}

	public double mediaPeriodo(Date deDate, Date ateDate, int i) {		
		//1 - "Média de Reservas",2 - "Média de Noites", 
		//3 - "Média de Diárias", 4 - "Média de Gastos Extras", 5 - "Média de Gasto Total", 
		//6 - "Média de Pessoas", 7 - "Média Ocupapação"

		Query query = entity.createQuery("SELECT t FROM Transacao t WHERE t.checkIn BETWEEN :dataInicial AND :dataFinal");
		query.setParameter("dataInicial", Utility.dateToSql(deDate));
		query.setParameter("dataFinal", Utility.dateToSql(ateDate));
		List<Transacao> list = query.getResultList();
		
		long quantidadeDias = (Utility.dateToSql(ateDate).getTime() - Utility.dateToSql(deDate).getTime()) + 3600000;
		quantidadeDias = (quantidadeDias / 86400000L) + 1;
		
		long cont = list.size();
		long soma = 0;
		int quantidadeNoites = 0;
		
		if (cont != 0 ) {
		
			switch (i) {
			//Realiza a media de reserva. Quantidade de reserva dividida pela quantidade de dias
			case 1:
				return cont / quantidadeDias;
			//Realiza a media de quantidade de noites. Quantidade de noites dividida pela quantidade de reserva.
			case 2:
				soma = 0;
				for (int j = 0; j < list.size(); j++) {
					soma += list.get(j).getQuantNoites();
				}
				return soma / cont;
			//Realiza a media de diarias. Valor da diaria vezes quantidade de noites dividido pela quantidade de reserva.
			case 3:
				soma = 0;
				quantidadeNoites = 0;
				for (int j = 0; j < list.size(); j++) {
					soma += list.get(j).getValUh();
					quantidadeNoites += list.get(j).getQuantNoites();
				}
				return soma / quantidadeNoites;
			case 4:
				soma = 0;
				for (int j = 0; j < list.size(); j++) {
					soma += list.get(j).getValoExtra();
				}
				return soma / cont;
			case 5:
				soma = 0;
				for (int j = 0; j < list.size(); j++) {
					soma += list.get(j).getValUh();
					soma += list.get(j).getValoExtra();
				}
				return soma / cont;
			case 6:
				soma = 0;
				for (int j = 0; j < list.size(); j++) {
					soma += list.get(j).getQuantHospedes();
				}
				return soma / cont;
			case 7:
				soma = 0;
				for (int j = 0; j < list.size(); j++) {
					soma += list.get(j).getQuantHospedes();
				}
				return soma / cont;
			}
			
		}
		
		return -1;
	}

}
