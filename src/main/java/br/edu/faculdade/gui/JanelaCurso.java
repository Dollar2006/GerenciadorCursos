package br.edu.faculdade.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.modelo.Curso;

public class JanelaCurso extends JDialog {
    private JTextField txtNome;
    private JSpinner spnCargaHoraria;
    private JSpinner spnLimiteAlunos;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private Curso curso;
    private boolean confirmado;
    private JPanel painelDados;
    
    public JanelaCurso(Frame owner, Curso curso) {
        super(owner, "Cadastro de Curso", true);
        this.curso = curso;
        this.confirmado = false;
        
        initComponents();
        if (curso != null) {
            preencherDados();
        }
        
        // Adicionando listener para redimensionamento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarTamanhoComponentes();
            }
        });
        
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Painel de dados
        painelDados = new JPanel(new GridBagLayout());
        painelDados.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblNome, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtNome, gbc);
        
        // Carga Horária
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel lblCargaHoraria = new JLabel("Carga Horária:");
        lblCargaHoraria.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblCargaHoraria, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerNumberModel modelCargaHoraria = new SpinnerNumberModel(20, 20, 1000, 1);
        spnCargaHoraria = new JSpinner(modelCargaHoraria);
        spnCargaHoraria.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(spnCargaHoraria, gbc);
        
        // Limite de Alunos
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel lblLimiteAlunos = new JLabel("Limite de Alunos:");
        lblLimiteAlunos.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblLimiteAlunos, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerNumberModel modelLimiteAlunos = new SpinnerNumberModel(1, 1, 100, 1);
        spnLimiteAlunos = new JSpinner(modelLimiteAlunos);
        spnLimiteAlunos.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(spnLimiteAlunos, gbc);
        
        add(painelDados, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        btnSalvar = new JButton("Salvar", new FlatSVGIcon("icons/save.svg", 24, 24));
        btnCancelar = new JButton("Cancelar", new FlatSVGIcon("icons/cancel.svg", 24, 24));
        
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        
        add(painelBotoes, BorderLayout.SOUTH);
        
        // Adicionando listeners
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }
    
    private void atualizarTamanhoComponentes() {
        int largura = getWidth();
        int altura = getHeight();
        
        // Calculando tamanho da fonte baseado no tamanho da janela
        int tamanhoFonte = Math.min(largura, altura) / 40;
        
        // Atualizando fonte dos componentes
        Font novaFonte = new Font("Segoe UI", Font.PLAIN, tamanhoFonte);
        Font novaFonteBold = new Font("Segoe UI", Font.BOLD, tamanhoFonte);
        
        for (Component comp : painelDados.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setFont(novaFonteBold);
            } else if (comp instanceof JTextField || comp instanceof JSpinner) {
                comp.setFont(novaFonte);
            }
        }
        
        btnSalvar.setFont(novaFonte);
        btnCancelar.setFont(novaFonte);
    }
    
    private void preencherDados() {
        txtNome.setText(curso.getNome());
        spnCargaHoraria.setValue(curso.getCargaHoraria());
        spnLimiteAlunos.setValue(curso.getLimiteAlunos());
    }
    
    private void salvar() {
        try {
            String nome = txtNome.getText().trim();
            int cargaHoraria = (Integer) spnCargaHoraria.getValue();
            int limiteAlunos = (Integer) spnLimiteAlunos.getValue();
            
            // Validações
            if (nome.length() < 3) {
                JOptionPane.showMessageDialog(this, 
                    "O nome do curso deve ter pelo menos 3 caracteres.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (curso == null) {
                curso = new Curso(nome, cargaHoraria, limiteAlunos);
            } else {
                curso.setNome(nome);
                curso.setCargaHoraria(cargaHoraria);
                curso.setLimiteAlunos(limiteAlunos);
            }
            
            CursoDAO dao = new CursoDAO();
            if (curso.getId() == 0) {
                dao.inserir(curso);
            } else {
                dao.atualizar(curso);
            }
            
            confirmado = true;
            dispose();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar curso: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Curso getCurso() {
        return curso;
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
} 