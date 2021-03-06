/*******************************************************************************
 * Copyright 2013-2017 alladin-IT GmbH
 * Copyright 2014-2016 SPECURE GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package at.alladin.rmbt.client;

public class Ping
{
    public Ping(long client, long server, long timeNs)
    {
        this.client = client;
        this.server = server;
        this.timeNs = timeNs;
    }

    public com.specure.nettest.shared.model.Ping toNewPingModel() {
        final com.specure.nettest.shared.model.Ping p = new com.specure.nettest.shared.model.Ping();
        p.setRelativeTimeNs(timeNs);
        p.setValue(client);
        p.setValueServer(server);
        return p;
    }

    final public long client;
    final public long server;
    final public long timeNs;
}
