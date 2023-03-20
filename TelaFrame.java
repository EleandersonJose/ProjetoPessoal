import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.EventListener;
import java.awt.EventQueue;

public class TelaFrame extends JFrame{
    
    private JPanel painel,contentPane;
    private JScrollPane scrollPane;
    private JTextField txtNomeUsuario, txtNomeTecnico,txtId;
   // private JTable tabela;
    JTable novoTesteTable;
    private JLabel lblUsuario, lblTecnico, lblId;
    private JButton botaoCadastrar, botaoApagar, botaoListar, botaoAtualizar, botaoBuscarPorID, btnU, btnT;
    private List<Chamados> listarChamados  = new ArrayList<>();
    Chamados chamados = new Chamados();

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Conexao conexao;
    CRUD crud = new CRUD();

    public TelaFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,720,640);
        setResizable(false);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(contentPane);

        painel = new JPanel();
        contentPane.add(painel, BorderLayout.CENTER);
        painel.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(0,200,500,200);

        lblId = new JLabel("ID: ");
        lblUsuario = new JLabel("USUARIO: ");
        lblTecnico = new JLabel("TECNICO: ");

        lblId.setBounds(24,79,46,14);
        lblUsuario.setBounds(24, 29, 64, 14);
        lblTecnico.setBounds(24, 54, 64, 14);

        txtId      = new JTextField();
        txtNomeUsuario = new JTextField();
        txtNomeTecnico  = new JTextField();

        txtId.setBounds(96, 79, 86, 20);
        txtNomeUsuario.setBounds(96, 26, 86, 20);
        txtNomeTecnico.setBounds(96, 51, 86, 20);

        botaoCadastrar = new JButton("Cadastrar");
        botaoApagar = new JButton("Apagar");
        botaoListar = new JButton("Listar");
        botaoAtualizar = new JButton("Atualizar");
        botaoBuscarPorID = new JButton("Buscar ID");
        btnU = new JButton("U");
        btnT = new JButton("T");

        botaoCadastrar.setBounds(52, 120, 101, 23);
        botaoApagar.setBounds(151, 120, 89, 23);
        botaoListar.setBounds(240,120,89,23);
        botaoAtualizar.setBounds(330, 120, 101, 23);
        botaoBuscarPorID.setBounds(190,79,101,19);
        btnU.setBounds(190,26,45,19);
        btnT.setBounds(190,51,45,19);

        botaoListar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              preencheTabela();
            }
        });

        botaoCadastrar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Clicou no botao cadastrar");
                Chamados salvarChamado = new Chamados();
                salvarChamado.setId(Integer.parseInt(txtId.getText()));
                salvarChamado.setNomeUsuario(txtNomeUsuario.getText());
                salvarChamado.setNomeTecnico(txtNomeTecnico.getText());
                crud.save(salvarChamado);
                limparCampos();
            }
        });

        botaoApagar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                crud.delete(Integer.parseInt(txtId.getText()));
                limparCampos();
                criaTabela();
                preencheTabela();
            }
        });

        botaoAtualizar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Chamados chamadoAtualizado = new Chamados();
                chamadoAtualizado.setId(Integer.parseInt(txtId.getText()));
                chamadoAtualizado.setNomeUsuario(txtNomeUsuario.getText());
                chamadoAtualizado.setNomeTecnico(txtNomeTecnico.getText());
                crud.update(chamadoAtualizado);
                limparCampos();
                criaTabela();
                preencheTabela();
            }
        });

        botaoBuscarPorID.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                preencheTabelaID(txtId.getText(), txtNomeUsuario.getText(), txtNomeTecnico.getText());
            }
        });

        btnU.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                preencheTabelaUsuario(txtNomeUsuario.getText());
            }
        });

        btnT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                preencheTabelaTecnico(txtNomeTecnico.getText());
            }
        });
        criaTabela();
        //novoTesteTable.addMouseListener(new MouseAdapter() {
          //  @Override
            //public void mouseClicked(MouseEvent e) {
              //  //chamando o metodo para setar os cmapos
                //setarCampos();
            //}
        //});
        
        painel.add(lblId);
        painel.add(lblTecnico);
        painel.add(lblUsuario);
        painel.add(txtId);
        painel.add(txtNomeUsuario);
        painel.add(txtNomeTecnico);
        painel.add(botaoCadastrar);
        painel.add(botaoApagar);
        painel.add(botaoListar);
        painel.add(botaoAtualizar);
        painel.add(botaoBuscarPorID);
        painel.add(btnU);
        painel.add(btnT);
        painel.add(scrollPane);
        setVisible(true);
    }

    public List<Chamados> lista(){
        criaTabela();
        Conexao conexao = new Conexao();
        conn = conexao.novaConexao();
        String sql = "SELECT * from teste";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                Chamados c1 = new Chamados();
                c1.setId(rs.getInt("id"));
                c1.setNomeUsuario(rs.getString("usuario"));
                c1.setNomeTecnico(rs.getString("tecnico"));
                listarChamados.add(c1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //for (Chamados c3 : listarChamados) {
          //  System.out.println(c3.toString());
        //}
        return listarChamados;
    }

    public void preencheTabela(){
        try{
            List<Chamados> listaLocal = lista();     
            DefaultTableModel modelo = (DefaultTableModel)novoTesteTable.getModel();

            for (int num = 0; num < listaLocal.size(); num++) {
                modelo.addRow(new Object[]{
                    listaLocal.get(num).getId(),
                    listaLocal.get(num).getNomeUsuario(),
                    listaLocal.get(num).getNomeTecnico()
                });
            }
        }catch(Exception e){
            e.getMessage();
        }
    }

    public void preencheTabelaID(String id, String usuario, String tecnico){

        DefaultTableModel modelo = (DefaultTableModel)novoTesteTable.getModel();
        modelo.setNumRows(0);
        CRUD crud = new CRUD();

        for (Chamados c : crud.buscarID(id, usuario, tecnico)) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNomeUsuario(),
                c.getNomeTecnico()
            });
        }
    }

    public void preencheTabelaUsuario(String usuario){

        DefaultTableModel modelo = (DefaultTableModel)novoTesteTable.getModel();
        modelo.setNumRows(0);
        CRUD crud = new CRUD();

        for (Chamados c : crud.buscarPorUsuario(usuario)) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNomeUsuario(),
                c.getNomeTecnico()
            });
        }
    }

    public void preencheTabelaTecnico(String tecnico){

        DefaultTableModel modelo = (DefaultTableModel)novoTesteTable.getModel();
        modelo.setNumRows(0);
        CRUD crud = new CRUD();

        for (Chamados c : crud.buscarPorTecnico(tecnico)) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNomeUsuario(),
                c.getNomeTecnico()
            });
        }
       // modelo.getColumnModel().
    }

    public void criaTabela(){
        novoTesteTable = new JTable();
        scrollPane.setViewportView(novoTesteTable);
        novoTesteTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "USUARIO", "TECNICO"
			}
		));
        novoTesteTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        novoTesteTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        novoTesteTable.getColumnModel().getColumn(2).setPreferredWidth(131);
        novoTesteTable.setEnabled(false);
    }

    public void setarCampos(){
        int setar = novoTesteTable.getSelectedRow();
        txtId.setText(novoTesteTable.getModel().getValueAt(setar, 0).toString());
        txtNomeUsuario.setText(novoTesteTable.getModel().getValueAt(setar, 1).toString());
        txtNomeTecnico.setText(novoTesteTable.getModel().getValueAt(setar, 2).toString());
    }

    public void limparCampos(){
        txtId.setText("");
        txtNomeTecnico.setText("");
        txtNomeUsuario.setText("");
    }

    public static void main(String[] args) {
        new TelaFrame();
    }
}