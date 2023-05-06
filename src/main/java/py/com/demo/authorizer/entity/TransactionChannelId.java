package py.com.demo.authorizer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionChannelId {
    POS_PURCHASE("pos_purchase"),
    BASE_1_VENTA("transactionChannelId_Base1_Venta"),
    BASE_1_OCT("transactionChannelId_Base1_OCT"),
    BASE_2_VENTA("transactionChannelId_Base2_Venta"),
    BASE_2_DEVOLUCION("transactionChannelId_Base2_Devolucion"),
    BASE_2_CHARGEBACK_VENTA("transactionChannelId_Base2_ChargeBack_Venta"),
    BASE_2_CHARGEBACK_DEVOLUCION("transactionChannelId_Base2_ChargeBack_Devolucion");

    private String id;

    public static TransactionChannelId getById(String id) {
        for (final TransactionChannelId tci: TransactionChannelId.values()) {
            if (id.equals(tci.getId())) {
                return tci;
            }
        }
        return null;
    }
}
