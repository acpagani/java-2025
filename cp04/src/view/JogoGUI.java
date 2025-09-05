package view;/*

@Author: Sistema de Gerenciamento de Biblioteca de Jogos
CP04 - JDBC + CRUD + Swing
Requisitos: Java 8+ e biblioteca sqlite-jdbc.jar no classpath.

*/

import dao.JogoDAO;
import model.Jogo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// --- Interface Gráfica (Swing) ---
public class JogoGUI extends JFrame {
    // Campos do formulário
    private JTextField txtId;
    private JTextField txtTitle;
    private JTextField txtGenre;
    private JTextField txtPlatform;
    private JTextField txtReleaseYear;
    private JComboBox<String> cmbStatus;
    private JTable tabela;
    private DefaultTableModel modelo;
    private final JogoDAO dao = new JogoDAO();

    // Componentes para funcionalidades extras
    private JComboBox<String> cmbFiltroGenero;
    private JComboBox<String> cmbFiltroPlataforma;
    private JComboBox<String> cmbFiltroStatus;
    private JTextArea txtRelatorio;
    private JSlider sliderNota;
    private JLabel lblNotaAtual;

    public JogoGUI() {
        initializeComponents();
        setupLayout();
        setupEvents();
        carregarDados();
    }

    private void initializeComponents() {
        setTitle("CP04 - Sistema de Gerenciamento de Biblioteca de Jogos");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Inicializar campos do formulário
        txtId = new JTextField(8);
        txtId.setEditable(false);
        txtTitle = new JTextField(20);
        txtGenre = new JTextField(15);
        txtPlatform = new JTextField(15);
        txtReleaseYear = new JTextField(8);

        // ComboBox para status
        cmbStatus = new JComboBox<>(new String[]{"Jogando", "Concluído", "Wishlist"});

        // Tabela
        modelo = new DefaultTableModel(new String[]{"ID", "Título", "Gênero", "Plataforma", "Ano", "Nota", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);

        // Inicializar componentes para funcionalidades extras
        cmbFiltroGenero = new JComboBox<>();
        cmbFiltroPlataforma = new JComboBox<>();
        cmbFiltroStatus = new JComboBox<>(new String[]{"Todos", "Jogando", "Concluído", "Wishlist"});

        // Slider para nota (0-10)
        sliderNota = new JSlider(0, 10, 5);
        sliderNota.setMajorTickSpacing(2);
        sliderNota.setMinorTickSpacing(1);
        sliderNota.setPaintTicks(true);
        sliderNota.setPaintLabels(true);
        lblNotaAtual = new JLabel("Nota: 5");

        // Área de relatório
        txtRelatorio = new JTextArea(8, 30);
        txtRelatorio.setEditable(false);
        txtRelatorio.setBackground(new Color(245, 245, 245));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel principal com abas
        JTabbedPane tabbedPane = new JTabbedPane();

        // === ABA 1: CRUD Principal ===
        JPanel painelCrud = new JPanel(new BorderLayout(10, 10));

        // Formulário
        JPanel formulario = new JPanel();
        formulario.setBorder(new TitledBorder("Cadastro de Jogos"));
        formulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Linha 1: ID e Título
        gbc.gridx = 0; gbc.gridy = 0;
        formulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        formulario.add(txtId, gbc);
        gbc.gridx = 2;
        formulario.add(new JLabel("Título:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 2;
        formulario.add(txtTitle, gbc);

        // Linha 2: Gênero e Plataforma
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        formulario.add(new JLabel("Gênero:"), gbc);
        gbc.gridx = 1;
        formulario.add(txtGenre, gbc);
        gbc.gridx = 2;
        formulario.add(new JLabel("Plataforma:"), gbc);
        gbc.gridx = 3;
        formulario.add(txtPlatform, gbc);

        // Linha 3: Ano e Status
        gbc.gridx = 0; gbc.gridy = 2;
        formulario.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        formulario.add(txtReleaseYear, gbc);
        gbc.gridx = 2;
        formulario.add(new JLabel("Status:"), gbc);
        gbc.gridx = 3;
        formulario.add(cmbStatus, gbc);

        // Linha 4: Nota
        gbc.gridx = 0; gbc.gridy = 3;
        formulario.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formulario.add(sliderNota, gbc);
        gbc.gridx = 3; gbc.gridwidth = 1;
        formulario.add(lblNotaAtual, gbc);

        painelCrud.add(formulario, BorderLayout.NORTH);

        // Painel intermediário para botões + tabela
        JPanel painelCentral = new JPanel(new BorderLayout(5, 5));

        // Botões CRUD
        JPanel painelBotoesCrud = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnInserir = new JButton("Inserir");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnDeletar = new JButton("Deletar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnListar = new JButton("Listar Todos");

        Dimension btnSize = new Dimension(100, 35);
        for (JButton btn : new JButton[]{btnInserir, btnAtualizar, btnDeletar, btnLimpar, btnListar}) {
            btn.setPreferredSize(btnSize);
            painelBotoesCrud.add(btn);
        }

        // Adicionar botões no topo do painel central
        painelCentral.add(painelBotoesCrud, BorderLayout.NORTH);

        // Tabela ocupa o centro do painel central
        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(new TitledBorder("Lista de Jogos"));
        painelCentral.add(scrollTabela, BorderLayout.CENTER);

        // Adicionar o painel central ao painel principal
        painelCrud.add(painelCentral, BorderLayout.CENTER);

        tabbedPane.addTab("CRUD Principal", painelCrud);

        // === ABA 2: Filtros e Relatórios ===
        JPanel painelFiltros = new JPanel(new BorderLayout(10, 10));

        // Painel de filtros
        JPanel filtros = new JPanel();
        filtros.setBorder(new TitledBorder("Filtros de Busca"));
        filtros.setLayout(new GridLayout(3, 2, 10, 10));

        filtros.add(new JLabel("Filtrar por Gênero:"));
        filtros.add(cmbFiltroGenero);
        filtros.add(new JLabel("Filtrar por Plataforma:"));
        filtros.add(cmbFiltroPlataforma);
        filtros.add(new JLabel("Filtrar por Status:"));
        filtros.add(cmbFiltroStatus);

        painelFiltros.add(filtros, BorderLayout.NORTH);

        // Área de relatório
        JScrollPane scrollRelatorio = new JScrollPane(txtRelatorio);
        scrollRelatorio.setBorder(new TitledBorder("Relatório"));
        painelFiltros.add(scrollRelatorio, BorderLayout.CENTER);

        // Botões de relatório
        JPanel painelBotoesRel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRelatorioGeral = new JButton("Relatório Geral");
        JButton btnRelatorioPorGenero = new JButton("Por Gênero");
        JButton btnRelatorioPorPlataforma = new JButton("Por Plataforma");
        JButton btnLimparRelatorio = new JButton("Limpar");

        for (JButton btn : new JButton[]{btnRelatorioGeral, btnRelatorioPorGenero, btnRelatorioPorPlataforma, btnLimparRelatorio}) {
            btn.setPreferredSize(new Dimension(130, 30));
            painelBotoesRel.add(btn);
        }

        painelFiltros.add(painelBotoesRel, BorderLayout.SOUTH);
        tabbedPane.addTab("Filtros e Relatórios", painelFiltros);

        add(tabbedPane, BorderLayout.CENTER);

        // === CONFIGURAR EVENTOS ===

        // Eventos CRUD
        btnInserir.addActionListener(e -> inserirJogo());
        btnAtualizar.addActionListener(e -> atualizarJogo());
        btnDeletar.addActionListener(e -> deletarJogo());
        btnLimpar.addActionListener(e -> limparCampos());
        btnListar.addActionListener(e -> listarTodos());

        // Eventos de filtro
        cmbFiltroGenero.addActionListener(e -> filtrarPorGenero());
        cmbFiltroPlataforma.addActionListener(e -> filtrarPorPlataforma());
        cmbFiltroStatus.addActionListener(e -> filtrarPorStatus());

        // Eventos de relatório
        btnRelatorioGeral.addActionListener(e -> gerarRelatorioGeral());
        btnRelatorioPorGenero.addActionListener(e -> gerarRelatorioPorGenero());
        btnRelatorioPorPlataforma.addActionListener(e -> gerarRelatorioPorPlataforma());
        btnLimparRelatorio.addActionListener(e -> txtRelatorio.setText(""));

        // Evento do slider
        sliderNota.addChangeListener(e -> lblNotaAtual.setText("Nota: " + sliderNota.getValue()));
    }

    private void setupEvents() {
        // Double-click na tabela para carregar nos campos
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tabela.getSelectedRow();
                    if (row >= 0) {
                        carregarJogoSelecionado(row);
                    }
                }
            }
        });
    }

    private void carregarDados() {
        listarTodos();
        atualizarCombosFiltro();
    }

    private void inserirJogo() {
        if (validarCampos()) return;

        try {
            // Validação para evitar jogos duplicados (título + plataforma)
            if (verificarJogoDuplicado(txtTitle.getText().trim(), txtPlatform.getText().trim())) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Já existe um jogo com este título nesta plataforma!",
                        "Jogo Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int ano = Integer.parseInt(txtReleaseYear.getText().trim());

            // Validação de ano futuro
            int anoAtual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            if (ano > anoAtual + 5) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Ano de lançamento muito distante no futuro!",
                        "Ano Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Jogo jogo = new Jogo(
                    txtTitle.getText().trim(),
                    txtGenre.getText().trim(),
                    txtPlatform.getText().trim(),
                    ano,
                    sliderNota.getValue(),
                    (String) cmbStatus.getSelectedItem()
            );

            dao.inserir(jogo);
            JOptionPane.showMessageDialog(this, "Jogo inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            listarTodos();
            limparCampos();
            atualizarCombosFiltro();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarJogo() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um jogo na tabela para atualizar.");
            return;
        }

        if (validarCampos()) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            int ano = Integer.parseInt(txtReleaseYear.getText().trim());

            Jogo jogo = new Jogo(
                    id,
                    txtTitle.getText().trim(),
                    txtGenre.getText().trim(),
                    txtPlatform.getText().trim(),
                    ano,
                    sliderNota.getValue(),
                    (String) cmbStatus.getSelectedItem()
            );

            dao.atualizar(jogo);
            JOptionPane.showMessageDialog(this, "Jogo atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            listarTodos();
            limparCampos();
            atualizarCombosFiltro();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID e Ano devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarJogo() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um jogo na tabela para deletar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String titulo = txtTitle.getText().trim();

            int resp = JOptionPane.showConfirmDialog(this,
                    "Confirmar exclusão do jogo:\n" + titulo + " (ID: " + id + ")?",
                    "Deletar Jogo", JOptionPane.YES_NO_OPTION);

            if (resp == JOptionPane.YES_OPTION) {
                dao.deletar(id);
                JOptionPane.showMessageDialog(this, "Jogo deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                listarTodos();
                limparCampos();
                atualizarCombosFiltro();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido para deletar.");
        }
    }

    private void listarTodos() {
        modelo.setRowCount(0);
        List<Jogo> jogos = dao.listar();
        for (Jogo jogo : jogos) {
            modelo.addRow(new Object[]{
                    jogo.getId(),
                    jogo.getTitle(),
                    jogo.getGenre(),
                    jogo.getPlatform(),
                    jogo.getReleaseYear(),
                    jogo.getRating(),
                    jogo.getStatus()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtTitle.setText("");
        txtGenre.setText("");
        txtPlatform.setText("");
        txtReleaseYear.setText("");
        cmbStatus.setSelectedIndex(0);
        sliderNota.setValue(5);
        lblNotaAtual.setText("Nota: 5");
        tabela.clearSelection();
        txtTitle.requestFocus();
    }

    private void carregarJogoSelecionado(int row) {
        txtId.setText(String.valueOf(tabela.getValueAt(row, 0)));
        txtTitle.setText(String.valueOf(tabela.getValueAt(row, 1)));
        txtGenre.setText(String.valueOf(tabela.getValueAt(row, 2)));
        txtPlatform.setText(String.valueOf(tabela.getValueAt(row, 3)));
        txtReleaseYear.setText(String.valueOf(tabela.getValueAt(row, 4)));
        sliderNota.setValue(Integer.parseInt(tabela.getValueAt(row, 5).toString()));
        cmbStatus.setSelectedItem(tabela.getValueAt(row, 6));
    }

    private boolean validarCampos() {
        if (txtTitle.getText().trim().isEmpty() ||
                txtGenre.getText().trim().isEmpty() ||
                txtPlatform.getText().trim().isEmpty() ||
                txtReleaseYear.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
            return true;
        }
        return false;
    }

    private boolean verificarJogoDuplicado(String titulo, String plataforma) {
        List<Jogo> jogos = dao.listar();
        return jogos.stream().anyMatch(jogo ->
                jogo.getTitle().equalsIgnoreCase(titulo) &&
                        jogo.getPlatform().equalsIgnoreCase(plataforma));
    }

    // === FUNCIONALIDADES EXTRAS ===

    private void atualizarCombosFiltro() {
        List<Jogo> jogos = dao.listar();

        // Atualizar combo de gêneros
        cmbFiltroGenero.removeAllItems();
        cmbFiltroGenero.addItem("Todos");
        jogos.stream().map(Jogo::getGenre).distinct().sorted().forEach(cmbFiltroGenero::addItem);

        // Atualizar combo de plataformas
        cmbFiltroPlataforma.removeAllItems();
        cmbFiltroPlataforma.addItem("Todos");
        jogos.stream().map(Jogo::getPlatform).distinct().sorted().forEach(cmbFiltroPlataforma::addItem);
    }

    private void filtrarPorGenero() {
        String generoSelecionado = (String) cmbFiltroGenero.getSelectedItem();
        if (generoSelecionado == null || generoSelecionado.equals("Todos")) {
            listarTodos();
            return;
        }

        modelo.setRowCount(0);
        List<Jogo> jogos = dao.listarPorGenero(generoSelecionado);
        for (Jogo jogo : jogos) {
            modelo.addRow(new Object[]{
                    jogo.getId(), jogo.getTitle(), jogo.getGenre(),
                    jogo.getPlatform(), jogo.getReleaseYear(), jogo.getStatus()
            });
        }
    }

    private void filtrarPorPlataforma() {
        String plataformaSelecionada = (String) cmbFiltroPlataforma.getSelectedItem();
        if (plataformaSelecionada == null || plataformaSelecionada.equals("Todos")) {
            listarTodos();
            return;
        }

        modelo.setRowCount(0);
        List<Jogo> jogos = dao.listarPorPlataforma(plataformaSelecionada);
        for (Jogo jogo : jogos) {
            modelo.addRow(new Object[]{
                    jogo.getId(), jogo.getTitle(), jogo.getGenre(),
                    jogo.getPlatform(), jogo.getReleaseYear(), jogo.getStatus()
            });
        }
    }

    private void filtrarPorStatus() {
        String statusSelecionado = (String) cmbFiltroStatus.getSelectedItem();
        if (statusSelecionado == null || statusSelecionado.equals("Todos")) {
            listarTodos();
            return;
        }

        modelo.setRowCount(0);
        List<Jogo> jogos = dao.listarPorStatus(statusSelecionado);
        for (Jogo jogo : jogos) {
            modelo.addRow(new Object[]{
                    jogo.getId(), jogo.getTitle(), jogo.getGenre(),
                    jogo.getPlatform(), jogo.getReleaseYear(), jogo.getStatus()
            });
        }
    }

    private void gerarRelatorioGeral() {
        List<Jogo> jogos = dao.listar();
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("=== RELATÓRIO GERAL DA BIBLIOTECA ===\n\n");
        relatorio.append("Total de jogos: ").append(jogos.size()).append("\n\n");

        // Estatísticas por status
        long jogando = jogos.stream().filter(j -> "Jogando".equals(j.getStatus())).count();
        long concluidos = jogos.stream().filter(j -> "Concluído".equals(j.getStatus())).count();
        long wishlist = jogos.stream().filter(j -> "Wishlist".equals(j.getStatus())).count();

        relatorio.append("Por Status:\n");
        relatorio.append("- Jogando: ").append(jogando).append("\n");
        relatorio.append("- Concluídos: ").append(concluidos).append("\n");
        relatorio.append("- Wishlist: ").append(wishlist).append("\n\n");

        // Estatísticas por plataforma
        relatorio.append("Por Plataforma:\n");
        Map<String, Long> jogosPorPlataforma = jogos.stream()
                .collect(Collectors.groupingBy(Jogo::getPlatform, Collectors.counting()));

        jogosPorPlataforma.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> relatorio.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));

        relatorio.append("\n");

        // Estatísticas por gênero
        relatorio.append("Por Gênero:\n");
        Map<String, Long> jogosPorGenero = jogos.stream()
                .collect(Collectors.groupingBy(Jogo::getGenre, Collectors.counting()));

        jogosPorGenero.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> relatorio.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));

        txtRelatorio.setText(relatorio.toString());
    }

    private void gerarRelatorioPorGenero() {
        List<Jogo> jogos = dao.listar();
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("=== RELATÓRIO POR GÊNERO ===\n\n");

        Map<String, List<Jogo>> jogosPorGenero = jogos.stream()
                .collect(Collectors.groupingBy(Jogo::getGenre));

        jogosPorGenero.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String genero = entry.getKey();
                    List<Jogo> jogosDoGenero = entry.getValue();

                    relatorio.append("GÊNERO: ").append(genero.toUpperCase()).append(" (").append(jogosDoGenero.size()).append(" jogos)\n");
                    relatorio.append("----------------------------------------\n");

                    jogosDoGenero.stream()
                            .sorted((j1, j2) -> j1.getTitle().compareToIgnoreCase(j2.getTitle()))
                            .forEach(jogo -> relatorio.append("• ").append(jogo.getTitle())
                                    .append(" (").append(jogo.getPlatform()).append(", ")
                                    .append(jogo.getReleaseYear()).append(") - ")
                                    .append(jogo.getStatus()).append("\n"));

                    relatorio.append("\n");
                });

        txtRelatorio.setText(relatorio.toString());
    }

    private void gerarRelatorioPorPlataforma() {
        List<Jogo> jogos = dao.listar();
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("=== RELATÓRIO POR PLATAFORMA ===\n\n");

        Map<String, List<Jogo>> jogosPorPlataforma = jogos.stream()
                .collect(Collectors.groupingBy(Jogo::getPlatform));

        jogosPorPlataforma.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())) // Ordem decrescente por quantidade
                .forEach(entry -> {
                    String plataforma = entry.getKey();
                    List<Jogo> jogosDaPlataforma = entry.getValue();

                    relatorio.append("PLATAFORMA: ").append(plataforma.toUpperCase()).append(" (").append(jogosDaPlataforma.size()).append(" jogos)\n");
                    relatorio.append("----------------------------------------\n");

                    jogosDaPlataforma.stream()
                            .sorted((j1, j2) -> j1.getTitle().compareToIgnoreCase(j2.getTitle()))
                            .forEach(jogo -> relatorio.append("• ").append(jogo.getTitle())
                                    .append(" (").append(jogo.getGenre()).append(", ")
                                    .append(jogo.getReleaseYear()).append(") - ")
                                    .append(jogo.getStatus()).append("\n"));

                    relatorio.append("\n");
                });

        txtRelatorio.setText(relatorio.toString());
    }
}