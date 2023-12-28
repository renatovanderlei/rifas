package com.devcaotics.sysRifa.model.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.devcaotics.sysRifa.model.entities.Aposta;
import com.devcaotics.sysRifa.model.entities.Apostador;
import com.devcaotics.sysRifa.model.entities.Premio;
import com.devcaotics.sysRifa.model.entities.Rifa;

@org.springframework.stereotype.Repository
public class RifaRepository implements Repository<Rifa, Long> {
	
	protected RifaRepository() {
		
	}

	@Override
	public Rifa create(Rifa t) throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "insert into Rifa (datacriacao, valoraposta, quantApostas, status)"
				+ "values (?,?,?,'ABERTO')";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		long dataHoraCriacao = System.currentTimeMillis();
		
		pstm.setTimestamp(1, new Timestamp(dataHoraCriacao));
		pstm.setDouble(2, t.getValorAposta());
		pstm.setInt(3, t.getQuantNumeros());
		
		pstm.execute();
		
		sql = "select * from Rifa order by datacriacao desc limit 1";
		
		PreparedStatement pstm2 = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		ResultSet res = pstm2.executeQuery();
		
		if(res.next()) {
			
			int codigo = res.getInt("codigo_Rifa");
			
			for(Premio p : t.getPremios()) {
				
				sql = "insert into Premio(descricao, "
						+ "cod_fk_rifa) value (?,?)";
				
				PreparedStatement pstm3 = ConnectionManager.getCurrentConnection().prepareStatement(sql);
				
				pstm3.setString(1, p.getDescricao());
				pstm3.setInt(2, codigo);
				
				pstm3.execute();
				
			}
			
		}
		return t;
		
	}
	
	public void addAposta(Rifa rifa, Aposta aposta) throws SQLException {
		
		String sql = "insert into aposta(numero, pago, cod_fk_apostador, cod_fk_rifa) values"
				+ "(?,?,?,?)";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		pstm.setInt(1, aposta.getNumero());
		pstm.setBoolean(2, aposta.isPago());
		pstm.setInt(3, aposta.getApostador().getCodigo());
		pstm.setInt(4, rifa.getCodigo());
		
		pstm.execute();
		
	}
	
	public void pagarAposta(Aposta a) throws SQLException {
		
		
		String sql = "update aposta set pago=? where codigo_aposta=?";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		pstm.setBoolean(1, a.isPago());
		pstm.setInt(2, a.getCodigo());
		
		pstm.execute();
		
		
	}
	
	@Override
	public Rifa read(int codigo) throws SQLException {
		String sql = "select * from rifa where codigo_rifa = " + codigo;
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		ResultSet result = pstm.executeQuery();
		
		if(result.next()) {
			Rifa f = new Rifa();
			
			f.setCodigo(result.getInt("codigo_rifa"));
			f.setDataCriacao(result.getTimestamp("datacriacao").getTime());
			Timestamp time = null;
			try {
				time = result.getTimestamp("dataSorteio");
			}catch(SQLException e) {}
				
			if(time != null) {
				f.setDataSorteio(time.getTime());
			}
			f.setValorAposta(result.getDouble("valorAposta"));
			f.setQuantNumeros(result.getInt("quantApostas"));
			
			f.setStatus(result.getString("status"));
			
			sql = "select * from premio where cod_fk_rifa="+f.getCodigo();
			
			ResultSet result2 = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
			
			f.setPremios(new ArrayList<Premio>());
			
			while(result2.next()) {
				
				Premio p = new Premio();
				p.setCodigo(result2.getInt("codigo_premio"));
				p.setDescricao(result2.getString("descricao"));
				
				f.getPremios().add(p);
				
			}
			
			sql = "select * from aposta where cod_fk_rifa = "+f.getCodigo();
			
			result2 = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
			
			while(result2.next()) {
				
				Aposta a = new Aposta();
				a.setCodigo(result2.getInt("codigo_aposta"));
				a.setNumero(result2.getInt("numero"));
				a.setPago(result2.getBoolean("pago"));
				
				sql = "select * from apostador where codigo_apostador = "+result2.getInt("cod_fk_apostador");
				
				ResultSet result3 = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
				
				if(result3.next()) {
					
					Apostador apostador = new Apostador();
					
					apostador.setCodigo(result3.getInt("codigo_apostador"));
					apostador.setEmail(result3.getString("email"));
					apostador.setLocalidade(result3.getString("localidade"));
					apostador.setNome(result3.getString("nome"));
					apostador.setWhatsapp(result3.getString("whatsapp"));
					
					a.setApostador(apostador);
					
				}
				
				f.addAposta(a);
				
				if(result2.getBoolean("vencedor")) {
					f.getVencedores().add(a);
				}
				
			}
			
			return f;
			
		}
		
		return null;
	}
	
	public void realizarSorteio(Rifa rifa) throws SQLException {
		
		String sql = "update rifa set dataSorteio=?, status=? where codigo_rifa=?";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		pstm.setTimestamp(1, new Timestamp(rifa.getDataSorteio()));
		pstm.setString(2, rifa.getStatus());
		pstm.setInt(3, rifa.getCodigo());
		
		pstm.execute();
		
		for(Aposta a: rifa.getVencedores()) {
			
			sql = "update aposta set vencedor=? where codigo_aposta=?";
			
			pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
			
			pstm.setBoolean(1, true);
			pstm.setInt(2, a.getCodigo());
			
			pstm.execute();
			
		}
		
	}
	

	@Override
	public Rifa update(Rifa t, Long codigo) throws SQLException {
		// TODO Auto-generated method stub
		throw new SQLException("This method is not implemented");
	}
	
	@Override
	public void delete(int codigo) throws SQLException {
		// TODO Auto-generated method stub
		throw new SQLException("This method is not implemented");
	}

	@Override
	public List<Rifa> readAll() throws SQLException {
		
		return getRifas("select * from rifa");
		
	}
	
	/*
	 * Seção de Filtros
	 */
	
	
	public List<Rifa> FilterByStatus(String status) throws SQLException{
		
		return this.getRifas("select  * from rifa where status = '"+status+"'");
		
	}
	
	
	/*
	 * Seção de métodos auxiliares
	 */
	private List<Rifa> getRifas(String sql) throws SQLException{
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		ResultSet result = pstm.executeQuery();
		
		List<Rifa> rifas = new ArrayList<>();
		
		while(result.next()) {
			Rifa f = new Rifa();
			
			f.setCodigo(result.getInt("codigo_rifa"));
			f.setDataCriacao(result.getTimestamp("datacriacao").getTime());
			Timestamp time = result.getTimestamp("dataSorteio");
			if(time != null) {
				f.setDataSorteio(time.getTime());
			}
			f.setValorAposta(result.getDouble("valorAposta"));
			f.setQuantNumeros(result.getInt("quantApostas"));
			
			f.setStatus(result.getString("status"));
			
			sql = "select * from premio where cod_fk_rifa="+f.getCodigo();
			
			ResultSet result2 = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
			
			f.setPremios(new ArrayList<Premio>());
			
			while(result2.next()) {
				
				Premio p = new Premio();
				p.setCodigo(result2.getInt("codigo_premio"));
				p.setDescricao(result2.getString("descricao"));
				
				f.getPremios().add(p);
				
			}
			
			sql = "select * from aposta where cod_fk_rifa = "+f.getCodigo();
			
			result2 = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
			
			while(result2.next()) {
				
				Aposta a = new Aposta();
				a.setCodigo(result2.getInt("codigo_aposta"));
				a.setNumero(result2.getInt("numero"));
				a.setPago(result2.getBoolean("pago"));
				
				sql = "select * from apostador where codigo_apostador = "+result2.getInt("cod_fk_apostador");
				
				ResultSet result3 = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();
				
				if(result3.next()) {
					
					Apostador apostador = new Apostador();
					
					apostador.setCodigo(result3.getInt("codigo_apostador"));
					apostador.setEmail(result3.getString("email"));
					apostador.setLocalidade(result3.getString("localidade"));
					apostador.setNome(result3.getString("nome"));
					apostador.setWhatsapp(result3.getString("whatsapp"));
					
					a.setApostador(apostador);
					
				}
				
				f.addAposta(a);
				
				if(result2.getBoolean("vencedor")) {
					f.getVencedores().add(a);
				}
				
			}
			
			rifas.add(f);
			
		}
		
		return rifas;
	}
	
	

}
