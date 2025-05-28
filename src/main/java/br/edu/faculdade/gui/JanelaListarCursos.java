package br.edu.faculdade.gui;

import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.modelo.Curso;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class JanelaListarCursos extends JDialog {
    private JTable tabelaCursos;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtivarDesativar;
    private JButton btnFechar;
    private CursoDAO cursoDAO;
    
    public JanelaListarCursos(Frame owner) {
        super(owner, "Lista de Cursos", true);
        cursoDAO = new CursoDAO();
        
        initComponents();
        carregarCursos();
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
            }
        });
        
        setSize(800, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        // Painel central com BoxLayout
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        // Criando a tabela
        String[] colunas = {"ID", "Nome", "Carga Horária", "Limite de Alunos", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCursos = new JTable(modeloTabela);
        tabelaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaCursos);
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
        btnNovo.addActionListener(e -> novoCurso());
        btnEditar.addActionListener(e -> editarCurso());
        btnExcluir.addActionListener(e -> excluirCurso());
        btnAtivarDesativar.addActionListener(e -> ativarDesativarCurso());
        btnFechar.addActionListener(e -> dispose());
    }
    
    private void carregarCursos() {
        try {
            List<Curso> cursos = cursoDAO.listarTodos();
            modeloTabela.setRowCount(0);
            
            for (Curso curso : cursos) {
                Object[] linha = {
                    curso.getId(),
                    curso.getNome(),
                    curso.getCargaHoraria(),
                    curso.getLimiteAlunos(),
                    curso.isAtivo() ? "Ativo" : "Inativo"
                };
                modeloTabela.addRow(linha);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar cursos: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void novoCurso() {
        JanelaCurso janela = new JanelaCurso((Frame) getOwner(), null);
        janela.setVisible(true);
        
        if (janela.isConfirmado()) {
            carregarCursos();
        }
    }
    
    private void editarCurso() {
        int linha = tabelaCursos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um curso para editar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            Curso curso = cursoDAO.buscarPorId(id);
            
            if (curso != null) {
                JanelaCurso janela = new JanelaCurso((Frame) getOwner(), curso);
                janela.setVisible(true);
                
                if (janela.isConfirmado()) {
                    carregarCursos();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao buscar curso: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirCurso() {
        int linha = tabelaCursos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um curso para excluir.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir este curso?\nTodos os alunos vinculados também serão excluídos.",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                cursoDAO.excluir(id);
                carregarCursos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir curso: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void ativarDesativarCurso() {
        int linha = tabelaCursos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um curso para ativar/desativar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            Curso curso = cursoDAO.buscarPorId(id);
            
            if (curso != null) {
                curso.setAtivo(!curso.isAtivo());
                cursoDAO.atualizar(curso);
                carregarCursos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao atualizar status do curso: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 