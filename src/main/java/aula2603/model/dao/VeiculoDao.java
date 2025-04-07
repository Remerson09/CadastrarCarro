package aula2603.model.dao;

import aula2603.jdbc.MinhaConexao;
import aula2603.model.entity.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeiculoDao {
    Connection con = MinhaConexao.conexao();

    public List<Veiculo> veiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculo";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getLong("id"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setPreco(rs.getDouble("preco"));
                v.setAnoFabricacao(rs.getInt("anoFabricacao"));
                veiculos.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return veiculos;
    }

    public void salvar(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo (marca, modelo, preco, anoFabricacao) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, veiculo.getMarca());
            ps.setString(2, veiculo.getModelo());
            ps.setDouble(3, veiculo.getPreco());
            ps.setInt(4, veiculo.getAnoFabricacao());
            ps.execute();

            // Obter o ID gerado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    veiculo.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET marca=?, modelo=?, preco=?, anoFabricacao=? WHERE id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, veiculo.getMarca());
            ps.setString(2, veiculo.getModelo());
            ps.setDouble(3, veiculo.getPreco());
            ps.setInt(4, veiculo.getAnoFabricacao());
            ps.setLong(5, veiculo.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM veiculo WHERE id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Veiculo buscarPorId(Long id) {
        String sql = "SELECT * FROM veiculo WHERE id=?";
        Veiculo v = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v = new Veiculo();
                    v.setId(rs.getLong("id"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setPreco(rs.getDouble("preco"));
                    v.setAnoFabricacao(rs.getInt("anoFabricacao"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    public List<Veiculo> buscarPorModelo(String modeloBusca) {
        List<Veiculo> encontrados = new ArrayList<>();
        String sql = "SELECT * FROM veiculo WHERE LOWER(modelo) LIKE ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + modeloBusca.toLowerCase() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Veiculo v = new Veiculo();
                    v.setId(rs.getLong("id"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setPreco(rs.getDouble("preco"));
                    v.setAnoFabricacao(rs.getInt("anoFabricacao"));
                    encontrados.add(v);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encontrados;
    }

}