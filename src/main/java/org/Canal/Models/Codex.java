package org.Canal.Models;

import java.util.HashMap;

public class Codex {

    HashMap<String, HashMap<String, Object>> variables = new HashMap<>();

    public Codex() {
        variables.put("/", new HashMap<String, Object>() {{
            put("currency_symbol", "$");
            put("default_currency", "USD");
            put("exit_auto_logout", false);
            put("hard_enforce_flows", false);
            put("default_tax_rate", 0.085);
            put("auto_override_rcv_loc", false);
            put("use_deliveries", true);
        }});
        variables.put("ORGS", new HashMap<String, Object>() {{
            put("name", "Organizations");
            put("prefix", "");
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
        variables.put("DCSS", new HashMap<String, Object>() {{
            put("name", "Distribution Centers");
            put("prefix", "DC");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("CCS", new HashMap<String, Object>() {{
            put("name", "Cost Centers");
            put("prefix", "CC");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("VEND", new HashMap<String, Object>() {{
            put("name", "Vendors");
            put("prefix", "V");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("WHS", new HashMap<String, Object>() {{
            put("name", "Warehouses");
            put("prefix", "WH");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("CSTS", new HashMap<String, Object>() {{
            put("name", "Customers");
            put("prefix", "C");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("AREAS", new HashMap<String, Object>() {{
            put("name", "Areas");
            put("prefix", "A");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("BNS", new HashMap<String, Object>() {{
            put("name", "Bins");
            put("prefix", "BN");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("DPTS", new HashMap<String, Object>() {{
            put("name", "Bins");
            put("prefix", "BN");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("HR/POS", new HashMap<String, Object>() {{
            put("name", "Positions");
            put("prefix", "POS-RQ");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("EMPS", new HashMap<String, Object>() {{
            put("name", "Employees");
            put("prefix", "E");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("USRS", new HashMap<String, Object>() {{
            put("name", "Users");
            put("prefix", "U");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("LGS", new HashMap<String, Object>() {{
            put("name", "Ledgers");
            put("prefix", "GL");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("ORDS/PO", new HashMap<String, Object>() {{
            put("name", "Purchase Orders");
            put("prefix", "PO");
            put("commit_to_ledger", true);
            put("use_deliveries", true);
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("ORDS/PR", new HashMap<String, Object>() {{
            put("name", "Purchase Requisitions");
            put("prefix", "PR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("GR", new HashMap<String, Object>() {{
            put("name", "Goods Receipts");
            put("prefix", "GR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("GI", new HashMap<String, Object>() {{
            put("name", "Goods Issues");
            put("prefix", "GI");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("ITS", new HashMap<String, Object>() {{
            put("name", "Items");
            put("prefix", "X0");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("MTS", new HashMap<String, Object>() {{
            put("name", "Materials");
            put("prefix", "M0");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("CMPS", new HashMap<String, Object>() {{
            put("name", "Components");
            put("prefix", "CP0");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/CRRS", new HashMap<String, Object>() {{
            put("name", "Transportation Carriers");
            put("prefix", "CRR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/ODO", new HashMap<String, Object>() {{
            put("name", "Outbound Deliveries");
            put("prefix", "OBD");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/IDO", new HashMap<String, Object>() {{
            put("name", "Inbound Deliveries");
            put("prefix", "IND");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
        variables.put("TRANS/TRCKS", new HashMap<String, Object>() {{
            put("name", "Trucks");
            put("prefix", "TR");
            put("allow_archival", true);
            put("allow_deletion", true);
            put("automake_enabled", true);
            put("allow_batch_create", true);
        }});
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