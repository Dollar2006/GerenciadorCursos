package br.edu.faculdade.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.edu.faculdade.dao.AlunoDAO;
import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.model.Curso;

public class JanelaListarAlunos extends JDialog {
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    private JComboBox<Curso> cmbCurso;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtivarDesativar;
    private JButton btnFechar;
    private AlunoDAO alunoDAO;
    private CursoDAO cursoDAO;
    
    public JanelaListarAlunos(Frame owner) {
        super(owner, "Lista de Alunos", true);
        alunoDAO = new AlunoDAO();
        cursoDAO = new CursoDAO();
        
        initComponents();
        carregarCursos();
        carregarAlunos();
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
            }
        });
        
        setSize(1000, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        // Painel central com BoxLayout
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        // Painel de filtro
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.add(new JLabel("Curso:"));
        cmbCurso = new JComboBox<>();
        cmbCurso.addItem(null); // Opção para mostrar todos os cursos
        painelFiltro.add(cmbCurso);
        JButton btnFiltrar = new JButton("Filtrar");
        painelFiltro.add(btnFiltrar);
        add(painelFiltro, BorderLayout.NORTH);
        // Criando a tabela
        String[] colunas = {"ID", "Matrícula", "CPF", "Nome", "Email", "Telefone", "Curso", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.add(scrollPane);
        painelCentral.add(Box.createVerticalGlue());
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(32, 16, 32, 16));
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtivarDesativar = new JButton("Ativar/Desativar");
        btnFechar = new JButton("Fechar");
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtivarDesativar);
        painelBotoes.add(btnFechar);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.add(painelBotoes);
        add(painelCentral, BorderLayout.CENTER);
        // Adicionando listeners
        btnFiltrar.addActionListener(e -> carregarAlunos());
        btnNovo.addActionListener(e -> novoAluno());
        btnEditar.addActionListener(e -> editarAluno());
        btnExcluir.addActionListener(e -> excluirAluno());
        btnAtivarDesativar.addActionListener(e -> ativarDesativarAluno());
        btnFechar.addActionListener(e -> dispose());
    }
    
    private void carregarCursos() {
        try {
            List<Curso> cursos = cursoDAO.listarAtivos();
            cmbCurso.removeAllItems();
            cmbCurso.addItem(null); // Opção para mostrar todos os cursos
            
            for (Curso curso : cursos) {
                cmbCurso.addItem(curso);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar cursos: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarAlunos() {
        try {
            List<Aluno> alunos;
            Curso cursoSelecionado = (Curso) cmbCurso.getSelectedItem();
            if (cursoSelecionado == null) {
                alunos = alunoDAO.listarTodos();
            } else {
                alunos = alunoDAO.listarPorCurso(cursoSelecionado.getId());
            }
            modeloTabela.setRowCount(0);
            for (Aluno aluno : alunos) {
                Object[] linha = {
                    aluno.getId(),
                    aluno.getMatricula(),
                    aluno.getCpf(),
                    aluno.getNome(),
                    aluno.getEmail(),
                    aluno.getTelefone(),
                    aluno.getCurso() != null ? aluno.getCurso().getNome() : "",
                    aluno.isAtivo() ? "Ativo" : "Inativo"
                };
                modeloTabela.addRow(linha);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar alunos: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void novoAluno() {
        JanelaAluno janela = new JanelaAluno((Frame) getOwner(), null);
        janela.setVisible(true);
        if (janela.isConfirmado()) {
            carregarAlunos();
        }
    }
    
    private void editarAluno() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um aluno para editar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            Aluno aluno = alunoDAO.buscarPorId(id);
            if (aluno != null) {
                JanelaAluno janela = new JanelaAluno((Frame) getOwner(), aluno);
                janela.setVisible(true);
                if (janela.isConfirmado()) {
                    carregarAlunos();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao buscar aluno: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirAluno() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um aluno para excluir.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir este aluno?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                alunoDAO.excluir(id);
                carregarAlunos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir aluno: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void ativarDesativarAluno() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um aluno para ativar/desativar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            Aluno aluno = alunoDAO.buscarPorId(id);
            
            if (aluno != null) {
                aluno.setAtivo(!aluno.isAtivo());
                alunoDAO.atualizar(aluno);
                carregarAlunos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao atualizar status do aluno: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 