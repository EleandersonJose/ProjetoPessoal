import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Chamados {
    
    public Integer id;
    public String nomeUsuario;
    public Integer numeroChamado;
    public String nomeTecnico;

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Conexao conexao;

    @Override
    public String toString(){
        return "ID: " +  getId() + "\n" + 
               "Nome: " + getNomeUsuario() + "\n" +
               "Chamado: " + getNomeTecnico()+
               "\n";
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    public Integer getNumeroChamado() {
        return numeroChamado;
    }
    public void setNumeroChamado(Integer numeroChamado) {
        this.numeroChamado = numeroChamado;
    }
    public String getNomeTecnico() {
        return nomeTecnico;
    }
    public void setNomeTecnico(String nomeTecnico) {
        this.nomeTecnico = nomeTecnico;
    }
}