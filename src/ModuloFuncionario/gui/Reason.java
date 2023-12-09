package ModuloFuncionario.gui;

/**
 * Enumeração que representa os diferentes motivos ou razões para operações
 * relacionadas a reservas no sistema de gerenciamento de aluguel de veículos.
 */
public enum Reason {
    /**
     * Representa uma reserva marcada como 'no-show', indicando que o cliente
     * não apareceu para retirar o veículo na data agendada.
     */
    NOSHOW,

    /**
     * Indica que uma reserva foi cancelada. Pode ser usada para marcar reservas
     * que foram canceladas por clientes ou funcionários.
     */
    CANCELADA,

    /**
     * Usado para marcar uma reserva como concluída. Geralmente, isso indica que
     * o veículo foi devolvido e a reserva foi encerrada sem problemas.
     */
    CONCLUIDA,

    /**
     * Representa a efetivação de uma reserva. Isso pode ser usado quando uma
     * reserva é confirmada e o veículo está pronto para ser retirado pelo cliente.
     */
    EFETIVADA,

    /**
     * Indica a criação de uma nova reserva. Usado para operações que envolvem
     * a adição de uma nova reserva no sistema.
     */
    CREATE
}
