package br.edu.faculdade.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.model.Curso;
import br.edu.faculdade.util.RelatorioUtil;

public class JanelaRelatorios extends JDialog {
    private JComboBox<Curso> cmbCurso;
    private JButton btnListarAlunosCurso;
    private JButton btnListarCursosEAlunos;
    private JButton btnExportarAlunosAtivos;
    private JButton btnExportarAlunosInativos;
    private JButton btnFechar;
    
    private CursoDAO cursoDAO;
    
    public JanelaRelatorios(Frame owner) {
        super(owner, "Relatórios", true);
        this.cursoDAO = new CursoDAO();
        
        initComponents();
        carregarCursos();
        
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setResizable(true);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior com o ComboBox
        JPanel painelSuperior = new JPanel(new GridBagLayout());
        painelSuperior.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelSuperior.add(new JLabel("Selecione um curso:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cmbCurso = new JComboBox<>();
        cmbCurso.setPreferredSize(new Dimension(300, 30));
        cmbCurso.addItem(new Curso(0, "Selecione um curso", 0, 0, 0, true));
        painelSuperior.add(cmbCurso, gbc);
        
        // Painel central com os botões
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 2;
        
        btnListarAlunosCurso = new JButton("Listar Alunos do Curso");
        btnListarCursosEAlunos = new JButton("Listar Cursos e Alunos");
        btnExportarAlunosAtivos = new JButton("Exportar Alunos Ativos");
        btnExportarAlunosInativos = new JButton("Exportar Alunos Inativos");
        btnFechar = new JButton("Fechar");
        
        // Ajustar tamanho dos botões
        Dimension buttonSize = new Dimension(250, 35);
        btnListarAlunosCurso.setPreferredSize(buttonSize);
        btnListarCursosEAlunos.setPreferredSize(buttonSize);
        btnExportarAlunosAtivos.setPreferredSize(buttonSize);
        btnExportarAlunosInativos.setPreferredSize(buttonSize);
        btnFechar.setPreferredSize(buttonSize);
        
        gbc.gridy = 0;
        painelCentral.add(btnListarAlunosCurso, gbc);
        
        gbc.gridy = 1;
        painelCentral.add(btnListarCursosEAlunos, gbc);
        
        gbc.gridy = 2;
        painelCentral.add(btnExportarAlunosAtivos, gbc);
        
        gbc.gridy = 3;
        painelCentral.add(btnExportarAlunosInativos, gbc);
        
        gbc.gridy = 4;
        painelCentral.add(btnFechar, gbc);
        
        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        
        // Eventos
        btnListarAlunosCurso.addActionListener(e -> listarAlunosCurso());
        btnListarCursosEAlunos.addActionListener(e -> listarCursosEAlunos());
        btnExportarAlunosAtivos.addActionListener(e -> exportarAlunosAtivos());
        btnExportarAlunosInativos.addActionListener(e -> exportarAlunosInativos());
        btnFechar.addActionListener(e -> dispose());
    }
    
    private void carregarCursos() {
        try {
            List<Curso> cursos = cursoDAO.listarAtivos();
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
    
    private void listarAlunosCurso() {
        try {
            Curso curso = (Curso) cmbCurso.getSelectedItem();
            if (curso == null || curso.getId() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um curso.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar relatório");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("alunos_" + curso.getNome() + ".txt"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();
                RelatorioUtil.gerarRelatorioAlunosPorCurso(curso.getId(), arquivo.getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                    "Relatório gerado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void listarCursosEAlunos() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar relatório");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("cursos_e_alunos.txt"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();
                RelatorioUtil.gerarRelatorioCursosEAlunos(arquivo.getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                    "Relatório gerado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportarAlunosAtivos() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar relatório");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("alunos_ativos.txt"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();
                RelatorioUtil.gerarRelatorioAlunosAtivosPorCurso(arquivo.getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                    "Relatório gerado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportarAlunosInativos() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar relatório");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("alunos_inativos.txt"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();
                RelatorioUtil.gerarRelatorioAlunosInativosPorCurso(arquivo.getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                    "Relatório gerado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 