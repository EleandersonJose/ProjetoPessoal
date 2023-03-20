import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CRUD {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Conexao conexao;
    List<Chamados> listaChamados = new ArrayList<>();

    public void save (Chamados chamado){
        conexao = new Conexao();
        String sql = "insert into teste(id, usuario, tecnico) values(?,?,?)";
        conn = conexao.novaConexao();

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, chamado.getId().toString());
            ps.setString(2,chamado.getNomeUsuario());
            ps.setString(3, chamado.getNomeTecnico());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Não foi possivel Salvar: " + e.getMessage());
            JOptionPane.showMessageDialog(null,"Não foi possivel Salvar:" + e.getMessage());
        }
    }
    
    public void delete(int id){
        conexao = new Conexao();

        String sql = "delete from teste where id=?";
        
        try {
            conn = conexao.novaConexao();

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Não foi possiveo apagar: " + e.getMessage());
        }
    }

   public void update(Chamados chamado){
        conexao = new Conexao();
        

        String sql = "UPDATE teste set usuario = ?, tecnico = ? where id = ?";

        try{
            conn = conexao.novaConexao();

            ps = conn.prepareStatement(sql);
            
            ps.setString(1, chamado.getNomeUsuario());
            ps.setString(2, chamado.getNomeTecnico());
            ps.setInt(3, chamado.getId());
            ps.execute();
        }catch(SQLException e){
            System.out.println("Falha ao atualizar" + e.getMessage());
        }

   } 

   public List<Chamados> buscarID(String id, String usuario, String tecnico){
        conexao = new Conexao();
        List<Chamados> listaLocal = new ArrayList<>();
        String sql = "Select * from teste where id like ? ";//and usuario ? and tecnico ?

        try {
            conn = conexao.novaConexao();
            ps = conn.prepareStatement(sql);

            ps.setString(1, "%"+id+"%");
            //ps.setString(2, "%"+usuario +"%");
            //ps.setString(3, "%"+tecnico+"%");
            rs = ps.executeQuery();

            while(rs.next()){
                Chamados c = new Chamados();
                c.setId(rs.getInt("id"));
                c.setNomeUsuario(rs.getString("usuario"));
                c.setNomeTecnico(rs.getString("tecnico"));
                listaLocal.add(c);
            }
        } catch (Exception e) {
            System.out.println("ERRO AO BUSCAR POR ID" + e.getMessage());
        }
        return listaLocal;
    }

    public List<Chamados> buscarPorUsuario(String usuario){
        conexao = new Conexao();
        List<Chamados> listaUsuario = new ArrayList<>();
        String sql = "Select * from teste where usuario like ? ";

        try{
            conn = conexao.novaConexao();
            ps = conn.prepareStatement(sql);

            ps.setString(1, "%"+usuario+"%");
            rs = ps.executeQuery();

            while(rs.next()){
                Chamados c = new Chamados();
                c.setId(rs.getInt("id"));
                c.setNomeUsuario(rs.getString("usuario"));
                c.setNomeTecnico(rs.getString("tecnico"));
                listaUsuario.add(c);
            }
        }catch(Exception e){
            System.out.println("ERRO AO BUSCAR POR USUARIO" + e.getMessage());
        }

        return listaUsuario;
    }

    public List<Chamados> buscarPorTecnico(String tecnico){
        conexao = new Conexao();
        List<Chamados> listaTecnico = new ArrayList<>();
        String sql = "Select * from teste where tecnico like ? ";

        try{
            conn = conexao.novaConexao();
            ps = conn.prepareStatement(sql);

            ps.setString(1, "%"+tecnico+"%");
            rs = ps.executeQuery();

            while(rs.next()){
                Chamados c = new Chamados();
                c.setId(rs.getInt("id"));
                c.setNomeUsuario(rs.getString("usuario"));
                c.setNomeTecnico(rs.getString("tecnico"));
                listaTecnico.add(c);
            }
        }catch(Exception e){
            System.out.println("ERRO AO BUSCAR POR TECNICO" + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaTecnico;
    }
}
