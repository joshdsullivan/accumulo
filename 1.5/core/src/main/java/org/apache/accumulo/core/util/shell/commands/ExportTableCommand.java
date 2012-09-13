/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.core.util.shell.commands;

import java.util.Map;
import java.util.Set;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.util.shell.Shell;
import org.apache.accumulo.core.util.shell.Shell.Command;
import org.apache.accumulo.core.util.shell.Token;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ExportTableCommand extends Command {
  
  private Option tableOpt;

  @Override
  public int execute(String fullCommand, CommandLine cl, Shell shellState) throws AccumuloException, AccumuloSecurityException, TableNotFoundException,
      TableExistsException {
    
    String tableName = OptUtil.getTableOpt(cl, shellState);

    shellState.getConnector().tableOperations().exportTable(tableName, cl.getArgs()[0]);
    return 0;
  }
  
  @Override
  public String usage() {
    return getName() + " <export dir>";
  }
  
  @Override
  public Options getOptions() {
    Options o = new Options();
    
    tableOpt = new Option(Shell.tableOption, "table", true, "table to export");
    
    tableOpt.setArgName("table");
    
    o.addOption(tableOpt);

    return o;
  }
  
  @Override
  public String description() {
    return "exports a table";
  }
  
  public void registerCompletion(Token root, Map<Command.CompletionSet,Set<String>> completionSet) {
    registerCompletionForTables(root, completionSet);
  }
  
  @Override
  public int numArgs() {
    return 1;
  }
}