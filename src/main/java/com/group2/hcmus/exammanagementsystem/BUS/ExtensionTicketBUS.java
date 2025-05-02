package com.group2.hcmus.exammanagementsystem.BUS;

import com.group2.hcmus.exammanagementsystem.DAO.ExtensionTicketDAO;
import com.group2.hcmus.exammanagementsystem.DTO.ExtensionTicketDTO;

public class ExtensionTicketBUS {
    private ExtensionTicketDAO extensionTicketDAO;

    public ExtensionTicketBUS() {
        this.extensionTicketDAO = new ExtensionTicketDAO();
    }

    public ExtensionTicketBUS(ExtensionTicketDAO extensionTicketDAO) {
        this.extensionTicketDAO = extensionTicketDAO;
    }

    /**
     * Save an extension ticket to the database
     * @param ticket The extension ticket to save
     * @return True if saved successfully, false otherwise
     */
    public boolean saveExtensionTicket(ExtensionTicketDTO ticket) {
        return extensionTicketDAO.saveExtensionTicket(ticket);
    }

    /**
     * Get the number of extensions for an exam card
     * @param maPhieuDuThi The exam card ID
     * @return The number of extensions
     */
    public int getExtensionCount(int maPhieuDuThi) {
        return extensionTicketDAO.getExtensionCount(maPhieuDuThi);
    }

    /**
     * Calculate extension fee based on type and reason
     * @param extensionType The type of extension
     * @param reason The reason for extension
     * @return The calculated fee
     */
    public double calculateExtensionFee(String extensionType, String reason) {
        return extensionTicketDAO.calculateExtensionFee(extensionType, reason);
    }
}
