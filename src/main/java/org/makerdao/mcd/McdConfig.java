/*
 * This library or application is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License (AGPL) as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any later version.
 *
 * This library or application is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License (AGPL) for
 * more details.
 *
 */
package org.makerdao.mcd;

import org.json.simple.parser.JSONParser;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetVersion;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

class McdConfig {

    private static final String MAINNET = "1";

    private Map<String, String> addresses;

    McdConfig(Web3j web3j) throws Exception {
        String configFile = getMcdConfigFile(web3j);

        File file = new File(ClassLoader.getSystemClassLoader().getResource(configFile).getFile());
        FileReader reader = new FileReader(file);
        addresses = ((Map<String, String>) new JSONParser().parse(reader));
    }

    String getDssProxyActionsDsrAddress() {
        return addresses.get("PROXY_ACTIONS_DSR");
    }

    String getPotAddress() {
        return addresses.get("MCD_POT");
    }

    String getVatAddress() {
        return addresses.get("MCD_VAT");
    }

    private String getMcdConfigFile(Web3j web3j) throws IOException {
        NetVersion version = web3j.netVersion().send();
        String network = version.getNetVersion();
        return network.equalsIgnoreCase(MAINNET) ? "mainnet.json" : "kovan.json";
    }
}