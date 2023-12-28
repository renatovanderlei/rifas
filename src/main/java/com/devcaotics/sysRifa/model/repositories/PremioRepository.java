package com.devcaotics.sysRifa.model.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.devcaotics.sysRifa.model.entities.Premio;

@org.springframework.stereotype.Repository

	public class PremioRepository implements Repository<Premio, Long> {

	@Override
	public Premio create(Premio e) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Premio update(Premio e, Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Premio read(int codigo) throws SQLException {
				
			String sql ="select * from Premio where codigo_premio=?";
			
			PreparedStatement pstm = ConnectionManager
					.getCurrentConnection().prepareStatement(sql);
			
			pstm.setInt(1, codigo);
			
			ResultSet result = pstm.executeQuery();
			
			if(result.next()) {
				
				Premio p = new Premio();
				
				p.setCodigo(result.getInt("codigo_premio"));
				p.setDescricao(result.getString("descricao"));
				
				return p;
				
			}
			
		return null;
	}

	@Override
	public void delete(int codigo) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Premio> readAll() throws SQLException {
		// TODO Auto-generated method stub
		String sql ="select * from Premio";
		
		PreparedStatement pstm = ConnectionManager
				.getCurrentConnection().prepareStatement(sql);
		
		
		ResultSet result = pstm.executeQuery();
		
		List<Premio> premios = new ArrayList<>();
		
		while(result.next()) {
			
			Premio p = new Premio();
			
			p.setCodigo(result.getInt("codigo_premio"));
			p.setDescricao(result.getString("descricao"));
			p.setCodigo_rifa(result.getInt("cod_fk_Rifa"));	
			premios.add(p);
			
		}
		
		return premios;
	}
}
