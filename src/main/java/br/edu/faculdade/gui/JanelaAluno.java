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
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import br.edu.faculdade.dao.AlunoDAO;
import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.model.Curso;
import br.edu.faculdade.util.ValidadorCPF;

public class JanelaAluno extends JDialog {
    private JTextField txtMatricula;
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JComboBox<Curso> cmbCurso;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private Aluno aluno;
    private boolean confirmado;
    private JPanel painelDados;
    
    public JanelaAluno(Frame owner, Aluno aluno) {
        super(owner, "Cadastro de Aluno", true);
        this.aluno = aluno;
        this.confirmado = false;
        
        initComponents();
        carregarCursos();
        if (aluno != null) {
            preencherDados();
        }
        
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
        
        // Matrícula
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblMatricula, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtMatricula = new JTextField(20);
        txtMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtMatricula, gbc);
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblCpf, gbc);
        
        gbc.gridx = 1;
        txtCpf = new JTextField(20);
        txtCpf.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtCpf, gbc);
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblNome, gbc);
        
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtNome, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtEmail, gbc);
        
        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblTelefone, gbc);
        
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        txtTelefone.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        painelDados.add(txtTelefone, gbc);
        
        // Curso
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelDados.add(lblCurso, gbc);
        
        gbc.gridx = 1;
        cmbCurso = new JComboBox<>();
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
        
        // Adicionando máscara para CPF
        txtCpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                String cpf = txtCpf.getText();
                if (cpf.length() == 3 || cpf.length() == 7) {
                    txtCpf.setText(cpf + ".");
                } else if (cpf.length() == 11) {
                    txtCpf.setText(cpf + "-");
                }
            }
        });
    }
    
    private void atualizarTamanhoComponentes() {
        int largura = getWidth();
        int altura = getHeight();
        
        // Ajustando tamanho dos componentes
        txtMatricula.setPreferredSize(new Dimension(largura - 100, 40));
        txtCpf.setPreferredSize(new Dimension(200, 40));
        txtNome.setPreferredSize(new Dimension(largura - 100, 40));
        txtEmail.setPreferredSize(new Dimension(largura - 100, 40));
        txtTelefone.setPreferredSize(new Dimension(200, 40));
        cmbCurso.setPreferredSize(new Dimension(largura - 100, 40));
        
        // Ajustando tamanho dos botões
        btnSalvar.setPreferredSize(new Dimension(150, 50));
        btnCancelar.setPreferredSize(new Dimension(150, 50));
    }
    
    private void carregarCursos() {
        try {
            CursoDAO cursoDAO = new CursoDAO();
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
        txtMatricula.setText(aluno.getMatricula());
        txtCpf.setText(aluno.getCpf());
        txtNome.setText(aluno.getNome());
        txtEmail.setText(aluno.getEmail());
        txtTelefone.setText(aluno.getTelefone());
        if (aluno.getCurso() != null) {
            cmbCurso.setSelectedItem(aluno.getCurso());
        }
    }
    
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex).matcher(email).matches();
    }
    
    private boolean validarCpf(String cpf) {
        if (!cpf.matches("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$")) {
            return false;
        }
        return ValidadorCPF.validarCPF(cpf);
    }
    
    private void salvar() {
        try {
            String matricula = txtMatricula.getText().trim();
            String cpf = txtCpf.getText().trim();
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            Curso curso = (Curso) cmbCurso.getSelectedItem();
            
            // Validações
            if (matricula.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "A matrícula é obrigatória.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "O CPF é obrigatório.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!validarCpf(cpf)) {
                JOptionPane.showMessageDialog(this,
                    "CPF inválido. Verifique o formato (000.000.000-00) e os dígitos verificadores.",
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
            
            if (telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "O telefone é obrigatório.",
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
            
            AlunoDAO alunoDAO = new AlunoDAO();
            
            // Verificar se a matrícula já existe
            if (aluno == null || !aluno.getMatricula().equals(matricula)) {
                if (alunoDAO.existeMatricula(matricula)) {
                    JOptionPane.showMessageDialog(this,
                        "Esta matrícula já está cadastrada.",
                        "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Verificar se o CPF já existe
            if (aluno == null || !aluno.getCpf().equals(cpf)) {
                if (alunoDAO.existeCpf(cpf)) {
                    JOptionPane.showMessageDialog(this,
                        "Este CPF já está cadastrado.",
                        "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if (aluno == null) {
                aluno = new Aluno(matricula, cpf, nome, email, telefone, curso);
            } else {
                aluno.setMatricula(matricula);
                aluno.setCpf(cpf);
                aluno.setNome(nome);
                aluno.setEmail(email);
                aluno.setTelefone(telefone);
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