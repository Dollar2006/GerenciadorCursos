package br.edu.faculdade.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.model.Curso;

public class JanelaCurso extends JDialog {
    private JTextField txtNome;
    private JSpinner spnCargaHoraria;
    private JSpinner spnVagas;
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
        JLabel lblCargaHoraria = new JLabel("Carga Horária:");
        lblCargaHoraria.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblCargaHoraria, gbc);
        
        gbc.gridx = 1;
        SpinnerNumberModel modelCargaHoraria = new SpinnerNumberModel(20, 20, 9999, 1);
        spnCargaHoraria = new JSpinner(modelCargaHoraria);
        spnCargaHoraria.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(spnCargaHoraria, gbc);
        
        // Vagas
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblVagas = new JLabel("Vagas:");
        lblVagas.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblVagas, gbc);
        
        gbc.gridx = 1;
        SpinnerNumberModel modelVagas = new SpinnerNumberModel(1, 1, 9999, 1);
        spnVagas = new JSpinner(modelVagas);
        spnVagas.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(spnVagas, gbc);
        
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
        
        // Ajustando tamanho dos componentes
        txtNome.setPreferredSize(new Dimension(largura - 100, 40));
        spnCargaHoraria.setPreferredSize(new Dimension(200, 40));
        spnVagas.setPreferredSize(new Dimension(200, 40));
        
        // Ajustando tamanho dos botões
        btnSalvar.setPreferredSize(new Dimension(150, 50));
        btnCancelar.setPreferredSize(new Dimension(150, 50));
    }
    
    private void preencherDados() {
        txtNome.setText(curso.getNome());
        spnCargaHoraria.setValue(curso.getCargaHoraria());
        spnVagas.setValue(curso.getVagas());
    }
    
    private void salvar() {
        try {
            String nome = txtNome.getText().trim();
            int cargaHoraria = (Integer) spnCargaHoraria.getValue();
            int vagas = (Integer) spnVagas.getValue();
            
            // Validações
            if (nome.length() < 3) {
                JOptionPane.showMessageDialog(this, 
                    "O nome do curso deve ter pelo menos 3 caracteres.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (curso == null) {
                curso = new Curso(nome, cargaHoraria, vagas);
            } else {
                curso.setNome(nome);
                curso.setCargaHoraria(cargaHoraria);
                curso.setVagas(vagas);
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