import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {
   
    public Connection novaConexao() {
        
    Connection conn = null;
    String driver = "com.mysql.cj.jdbc.Driver";

    String user = "root";
    String pass = "12345678";
    String url = "jdbc:mysql://localhost:3306/dbChamados";
        
        try{
            try {
                Class.forName(driver);
                System.out.println("Driver localizado");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver nao localizado");
                e.printStackTrace();
            }
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado ao banco com sucesso");
        }catch(SQLException e){
            System.out.println(e);
        }
        return conn;
    }
}