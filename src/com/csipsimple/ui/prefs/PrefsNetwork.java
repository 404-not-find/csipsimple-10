/**
 * Copyright (C) 2010 Regis Montoya (aka r3gis - www.r3gis.fr)
 * This file is part of CSipSimple.
 *
 *  CSipSimple is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  CSipSimple is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with CSipSimple.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.csipsimple.ui.prefs;

import org.pjsip.pjsua.pjsua;
import org.pjsip.pjsua.pjsuaConstants;

import android.telephony.TelephonyManager;

import com.csipsimple.R;
import com.csipsimple.api.SipConfigManager;
import com.csipsimple.utils.Log;
import com.csipsimple.utils.PreferencesWrapper;


public class PrefsNetwork extends GenericPrefs {
	

	private static final String THIS_FILE = "Prefs Network";

	@Override
	protected int getXmlPreferences() {
		return R.xml.prefs_network;
		
	}
	
	@Override
	protected void afterBuildPrefs() {
		super.afterBuildPrefs();
		TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
		
		if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
			hidePreference("for_incoming", "use_gprs_in");
			hidePreference("for_outgoing", "use_gprs_out");
			hidePreference("for_incoming", "use_edge_in");
			hidePreference("for_outgoing", "use_edge_out");
		}
		PreferencesWrapper pfw = new PreferencesWrapper(this);
		if(!pfw.isAdvancedUser()) {
			hidePreference(null, "perfs");
			
			hidePreference("nat_traversal", SipConfigManager.ENABLE_TURN);
			hidePreference("nat_traversal", SipConfigManager.TURN_SERVER);
			
			hidePreference("transport", SipConfigManager.ENABLE_TCP);
			hidePreference("transport", SipConfigManager.ENABLE_UDP);
			hidePreference("transport", SipConfigManager.TCP_TRANSPORT_PORT);
			hidePreference("transport", SipConfigManager.UDP_TRANSPORT_PORT);
			hidePreference("transport", SipConfigManager.RTP_PORT);
			hidePreference("transport", SipConfigManager.USE_IPV6);
			hidePreference("transport", SipConfigManager.OVERRIDE_NAMESERVER);
			
			hidePreference("transport", SipConfigManager.ENABLE_QOS);
			hidePreference("transport", SipConfigManager.DSCP_VAL);
			
			
			
		}
		boolean canUseTLS = false;
		try{
			canUseTLS = (pjsua.can_use_tls() == pjsuaConstants.PJ_TRUE);
		}catch (Exception e) {
			Log.e(THIS_FILE, "Unable to get pjsua tls state");
		}
		if(!canUseTLS) {
			hidePreference(null, "secure_transport");
		}
	}

	@Override
	protected void updateDescriptions() {
		setStringFieldSummary("stun_server");
	}
	
}
