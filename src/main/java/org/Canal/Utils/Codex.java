package org.Canal.Utils;

import java.util.HashMap;

public class Codex {

    HashMap<String, HashMap<String, Object>> variables = new HashMap<>();

    public Codex() {
        variables.put("/", new HashMap<>() {{
            put("minumim_build", 1); //What Min build version should clients have?
            put("currency_symbol", "$");
            put("default_currency", "USD");
            put("exit_auto_logout", false);
            put("hard_enforce_flows", false);
            put("default_tax_rate", 0.085);
            put("auto_override_rcv_loc", false);
            put("use_deliveries", true);
            put("perform_ff_stock_check", false);
        }});
        variables.put("ORGS", new HashMap<>() {{
            put("name", "Organizations");
            put("prefix", "");
            put("icon", "organizations");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("auto_putaway", false);
            put("autogen_gr_on_receive", true);
            put("automake_enabled", true);
            put("autowave_enabled", false);
            put("calculate_bin_size", false);
            put("check_area_size", false);
            put("check_bin_size", false);
            put("list_refresh_rate", 10);
            put("require_palletization", false);
            put("repeat_create", false);
            put("single_order_pur_req", false);
            put("hu_length", 10);
        }});
        variables.put("DCSS", new HashMap<>() {{
            put("name", "Distribution Centers");
            put("icon", "distributioncenters");
            put("prefix", "DC");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("CCS", new HashMap<>() {{
            put("name", "Cost Centers");
            put("icon", "costcenters");
            put("prefix", "CC");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("VEND", new HashMap<>() {{
            put("name", "Vendors");
            put("icon", "vendors");
            put("prefix", "V");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("WHS", new HashMap<>() {{
            put("name", "Warehouses");
            put("icon", "warehouses");
            put("prefix", "WH");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("CSTS", new HashMap<>() {{
            put("name", "Customers");
            put("icon", "customers");
            put("prefix", "C");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("ACCS", new HashMap<>() {{
            put("name", "Accounts");
            put("icon", "accounts");
            put("prefix", "#");
            put("require_agreements", false);
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("AREAS", new HashMap<>() {{
            put("name", "Areas");
            put("icon", "areas");
            put("prefix", "A");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("BNS", new HashMap<>() {{
            put("name", "Bins");
            put("icon", "bins");
            put("prefix", "BN");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("DPTS", new HashMap<>() {{
            put("name", "Bins");
            put("icon", "bins");
            put("prefix", "BN");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("HR/POS", new HashMap<>() {{
            put("name", "Positions");
            put("icon", "positions");
            put("prefix", "POS-RQ");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("EMPS", new HashMap<>() {{
            put("name", "People");
            put("icon", "employees");
            put("prefix", "E");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("USRS", new HashMap<>() {{
            put("name", "Users");
            put("icon", "users");
            put("prefix", "U");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("LGS", new HashMap<>() {{
            put("name", "Ledgers");
            put("icon", "ledgers");
            put("prefix", "GL");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("ORDS/PO", new HashMap<>() {{
            put("name", "Purchase Orders");
            put("icon", "orders");
            put("prefix", "PO");
            put("commit_to_ledger", false);
            put("use_deliveries", true);
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
            put("require_pr", true);
            put("vendor_match", true);
        }});
        variables.put("ORDS/SO", new HashMap<>() {{
            put("name", "Sales Orders");
            put("icon", "orders");
            put("prefix", "SO");
            put("commit_to_ledger", true);
            put("use_deliveries", true);
            put("auto_create_po", true);
            put("create_buyer_inbound", true);
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("ORDS/PR", new HashMap<>() {{
            put("name", "Purchase Requisitions");

            put("prefix", "PR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("GR", new HashMap<>() {{
            put("name", "Goods Receipts");
            put("prefix", "GR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("GI", new HashMap<>() {{
            put("name", "Goods Issues");
            put("prefix", "GI");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("CATS", new HashMap<>() {{
            put("name", "Catalogs");
            put("prefix", "CT");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("ITS", new HashMap<>() {{
            put("name", "Items");
            put("prefix", "X0");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("MTS", new HashMap<>() {{
            put("name", "Materials");
            put("prefix", "M0");
            put("allow_modification", true);
            put("allow_archival", true);
            put("allow_deletion", true);
        }});
        variables.put("CMPS", new HashMap<>() {{
            put("name", "Components");
            put("prefix", "CP0");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/CRRS", new HashMap<>() {{
            put("name", "Transportation Carriers");
            put("icon", "carriers");
            put("prefix", "CRR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/ODO", new HashMap<>() {{
            put("name", "Outbound Deliveries");
            put("prefix", "OBD");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/IDO", new HashMap<>() {{
            put("name", "Inbound Deliveries");
            put("prefix", "IND");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/TRCKS", new HashMap<>() {{
            put("name", "Trucks");
            put("prefix", "TR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
    }

    public HashMap<String, HashMap<String, Object>> getVariables() {
        return variables;
    }

    public Object getValue(String outerKey, String innerKey) {
        if(outerKey.startsWith("/")){
            outerKey = outerKey.toUpperCase().replace("/", "");
        }
        HashMap<String, Object> innerMap = variables.get(outerKey);
        if (innerMap != null) {
            return innerMap.get(innerKey);
        }
        return null; // Return null if the outer or inner key does not exist
    }
}