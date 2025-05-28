package br.edu.faculdade.gui;

import br.edu.faculdade.dao.AlunoDAO;
import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.modelo.Aluno;
import br.edu.faculdade.modelo.Curso;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class JanelaAluno extends JDialog {
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JDateChooser dateChooser;
    private JComboBox<Curso> cmbCurso;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private Aluno aluno;
    private boolean confirmado;
    private AlunoDAO alunoDAO;
    private CursoDAO cursoDAO;
    
    public JanelaAluno(Frame owner, Aluno aluno) {
        super(owner, "Cadastro de Aluno", true);
        this.aluno = aluno;
        this.confirmado = false;
        this.alunoDAO = new AlunoDAO();
        this.cursoDAO = new CursoDAO();
        
        initComponents();
        carregarCursos();
        if (aluno != null) {
            preencherDados();
        }
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
            }
        });
        
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel painelDados = new JPanel(new GridBagLayout());
        painelDados.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblCpf, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtCpf = new JTextField(20);
        txtCpf.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtCpf, gbc);
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblNome, gbc);
        
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtNome, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtEmail, gbc);
        
        // Data de Nascimento
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblData = new JLabel("Data de Nascimento:");
        lblData.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblData, gbc);
        
        gbc.gridx = 1;
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(dateChooser, gbc);
        
        // Curso
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblCurso, gbc);
        
        gbc.gridx = 1;
        cmbCurso.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(cmbCurso, gbc);
        
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
    
    private void carregarCursos() {
        try {
            List<Curso> cursos = cursoDAO.listarAtivos();
            cmbCurso.removeAllItems();
            
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
    
    private void preencherDados() {
        txtCpf.setText(aluno.getCpf());
        txtNome.setText(aluno.getNome());
        txtEmail.setText(aluno.getEmail());
        dateChooser.setDate(Date.from(aluno.getDataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        cmbCurso.setSelectedItem(aluno.getCurso());
    }
    
    private boolean validarCpf(String cpf) {
        return cpf.matches("\\d{11}");
    }
    
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex).matcher(email).matches();
    }
    
    private void salvar() {
        try {
            String cpf = txtCpf.getText().trim();
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            LocalDate dataNascimento = dateChooser.getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
            Curso curso = (Curso) cmbCurso.getSelectedItem();
            
            // Validações
            if (!validarCpf(cpf)) {
                JOptionPane.showMessageDialog(this,
                    "CPF inválido. Deve conter 11 dígitos numéricos.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (nome.length() < 3) {
                JOptionPane.showMessageDialog(this,
                    "O nome deve ter pelo menos 3 caracteres.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!validarEmail(email)) {
                JOptionPane.showMessageDialog(this,
                    "Email inválido.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (dataNascimento.plusYears(16).isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this,
                    "O aluno deve ter pelo menos 16 anos.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (curso == null) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um curso.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (aluno == null) {
                // Verificar se CPF já existe
                if (alunoDAO.existeCpf(cpf)) {
                    JOptionPane.showMessageDialog(this,
                        "CPF já cadastrado.",
                        "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                aluno = new Aluno(cpf, nome, email, dataNascimento, curso);
            } else {
                aluno.setCpf(cpf);
                aluno.setNome(nome);
                aluno.setEmail(email);
                aluno.setDataNascimento(dataNascimento);
                aluno.setCurso(curso);
            }
            
            if (aluno.getId() == 0) {
                alunoDAO.inserir(aluno);
            } else {
                alunoDAO.atualizar(aluno);
            }
            
            confirmado = true;
            dispose();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar aluno: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Aluno getAluno() {
        return aluno;
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
} 