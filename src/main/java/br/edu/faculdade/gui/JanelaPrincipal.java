package br.edu.faculdade.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class JanelaPrincipal extends JFrame {
    private static final int MIN_WIDTH = 1100;
    private static final int MIN_HEIGHT = 750;
    private JPanel painelPrincipal;
    
    public JanelaPrincipal() {
        setTitle("Sistema de Gerenciamento de Cursos e Alunos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(24, 26, 32)); // fundo escuro
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        
        // Criando o menu
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Cursos
        JMenu menuCursos = new JMenu("Cursos");
        JMenuItem itemNovoCurso = new JMenuItem("Novo Curso", new FlatSVGIcon("icons/new_course.svg", 16, 16));
        JMenuItem itemListarCursos = new JMenuItem("Listar Cursos", new FlatSVGIcon("icons/list_courses.svg", 16, 16));
        
        menuCursos.add(itemNovoCurso);
        menuCursos.add(itemListarCursos);
        
        // Menu Alunos
        JMenu menuAlunos = new JMenu("Alunos");
        JMenuItem itemNovoAluno = new JMenuItem("Novo Aluno", new FlatSVGIcon("icons/new_student.svg", 16, 16));
        JMenuItem itemListarAlunos = new JMenuItem("Listar Alunos", new FlatSVGIcon("icons/list_students.svg", 16, 16));
        
        menuAlunos.add(itemNovoAluno);
        menuAlunos.add(itemListarAlunos);
        
        // Menu Relatórios
        JMenu menuRelatorios = new JMenu("Relatórios");
        JMenuItem itemRelatorioCursos = new JMenuItem("Relatório de Cursos", new FlatSVGIcon("icons/report_courses.svg", 16, 16));
        JMenuItem itemRelatorioAlunos = new JMenuItem("Relatório de Alunos", new FlatSVGIcon("icons/report_students.svg", 16, 16));
        
        menuRelatorios.add(itemRelatorioCursos);
        menuRelatorios.add(itemRelatorioAlunos);
        
        menuBar.add(menuCursos);
        menuBar.add(menuAlunos);
        menuBar.add(menuRelatorios);
        
        setJMenuBar(menuBar);
        
        // Painel principal com GridBagLayout para melhor responsividade
        painelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 36, 42));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
            }
        };
        painelPrincipal.setOpaque(false);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Botões do painel principal
        JButton btnCursos = criarBotao("Gerenciar Cursos", "icons/courses.svg");
        JButton btnAlunos = criarBotao("Gerenciar Alunos", "icons/students.svg");
        JButton btnRelatorios = criarBotao("Relatórios", "icons/reports.svg");
        JButton btnSair = criarBotao("Sair", "icons/exit.svg");
        
        // Adicionando botões ao painel com GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(btnCursos, gbc);
        
        gbc.gridx = 1;
        painelPrincipal.add(btnAlunos, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelPrincipal.add(btnRelatorios, gbc);
        
        gbc.gridx = 1;
        painelPrincipal.add(btnSair, gbc);
        
        add(painelPrincipal);
        
        // Adicionando listeners
        itemNovoCurso.addActionListener(e -> abrirJanelaNovoCurso());
        itemListarCursos.addActionListener(e -> abrirJanelaListarCursos());
        itemNovoAluno.addActionListener(e -> abrirJanelaNovoAluno());
        itemListarAlunos.addActionListener(e -> abrirJanelaListarAlunos());
        itemRelatorioCursos.addActionListener(e -> abrirRelatorioCursos());
        itemRelatorioAlunos.addActionListener(e -> abrirRelatorioAlunos());
        
        btnCursos.addActionListener(e -> abrirJanelaListarCursos());
        btnAlunos.addActionListener(e -> abrirJanelaListarAlunos());
        btnRelatorios.addActionListener(e -> abrirRelatorioCursos());
        btnSair.addActionListener(e -> System.exit(0));
        
        // Adicionando listener para redimensionamento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarTamanhoBotoes();
            }
        });
    }
    
    private JButton criarBotao(String texto, String caminhoIcone) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 28));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(44, 47, 56));
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 200), 2, true),
            BorderFactory.createEmptyBorder(32, 48, 32, 48)
        ));
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setVerticalTextPosition(SwingConstants.BOTTOM);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);
        
        // Carregando ícone SVG
        FlatSVGIcon icone = new FlatSVGIcon(caminhoIcone, 72, 72);
        botao.setIcon(icone);
        
        // Efeito hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(60, 70, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(44, 47, 56));
            }
        });
        
        return botao;
    }
    
    private void atualizarTamanhoBotoes() {
        int largura = getWidth();
        int altura = getHeight();
        // Calculando tamanho dos botões baseado no tamanho da janela
        int tamanhoBotao = Math.min(largura, altura) / 4;
        for (Component comp : painelPrincipal.getComponents()) {
            if (comp instanceof JButton) {
                JButton botao = (JButton) comp;
                // FlatSVGIcon icone = (FlatSVGIcon) botao.getIcon();
                // icone.setWidth(tamanhoBotao / 2);
                // icone.setHeight(tamanhoBotao / 2);
                // botao.setIcon(icone);
                botao.setFont(new Font("Segoe UI", Font.BOLD, tamanhoBotao / 16));
            }
        }
    }
    
    private void abrirJanelaNovoCurso() {
        JanelaCurso janela = new JanelaCurso(this, null);
        janela.setVisible(true);
    }
    
    private void abrirJanelaListarCursos() {
        JanelaListarCursos janela = new JanelaListarCursos(this);
        janela.setVisible(true);
    }
    
    private void abrirJanelaNovoAluno() {
        JanelaAluno janela = new JanelaAluno(this, null);
        janela.setVisible(true);
    }
    
    private void abrirJanelaListarAlunos() {
        JanelaListarAlunos janela = new JanelaListarAlunos(this);
        janela.setVisible(true);
    }
    
    private void abrirRelatorioCursos() {
        JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento");
    }
    
    private void abrirRelatorioAlunos() {
        JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Configurando o FlatLaf escuro
                UIManager.setLookAndFeel(new FlatDarkLaf());
                // Cores e fontes globais maiores
                UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 28));
                UIManager.put("Label.font", new Font("Segoe UI", Font.BOLD, 22));
                UIManager.put("Label.foreground", Color.WHITE);
                UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 22));
                UIManager.put("TextField.background", new Color(34, 36, 42));
                UIManager.put("TextField.foreground", Color.WHITE);
                UIManager.put("TextField.caretForeground", Color.WHITE);
                UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 22));
                UIManager.put("ComboBox.background", new Color(34, 36, 42));
                UIManager.put("ComboBox.foreground", Color.WHITE);
                UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 20));
                UIManager.put("Table.background", new Color(34, 36, 42));
                UIManager.put("Table.foreground", Color.WHITE);
                UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 22));
                UIManager.put("TableHeader.background", new Color(44, 47, 56));
                UIManager.put("TableHeader.foreground", Color.WHITE);
                new JanelaPrincipal().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
} 