/*******************************************************************************
 * Copyright 2016-2017 alladin-IT GmbH
 * Copyright 2016 SPECURE GmbH
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

package com.specure.nettest.spring.data.couchdb.support;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//
//import org.yaml.snakeyaml.Yaml;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.specure.nettest.spring.data.couchdb.api.CouchDbCrudRepository;
//
///**
// * Loads a document into the database. Useful for the initialization of a database.
// * 
// * @author rwitzel
// */
//public class DocumentLoader {
//
//    @SuppressWarnings("rawtypes")
//    private CouchDbCrudRepository<Map, String> repository;
//
//    /**
//     * @param repository
//     *            A repository for {@link Map maps}.
//     */
//    @SuppressWarnings("rawtypes")
//    public DocumentLoader(CouchDbCrudRepository<Map, String> repository) {
//        super();
//        this.repository = repository;
//    }
//
//    /**
//     * Loads the given JSON document into the database. Overrides an existing document.
//     * 
//     * @param documentContent
//     *            the JSON content of a document
//     */
//    public void loadJson(InputStream documentContent) {
//        save(parseJson(documentContent));
//    }
//
//    /**
//     * Loads the given YAML document into the database. Overrides an existing document.
//     * 
//     * @param documentContent
//     *            the YAML content of a document
//     */
//    public void loadYaml(InputStream documentContent) {
//        save(parseYaml(documentContent));
//    }
//
//    @SuppressWarnings("unchecked")
//    public void save(Map<String, Object> document) {
//
//        String documentId = (String) document.get("_id");
//
//        if (repository.exists(documentId)) {
//            Map<String, Object> documentInDb = repository.findOne(documentId);
//            document.put("_rev", documentInDb.get("_rev"));
//        }
//
//        repository.save(document);
//    }
//
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> parseJson(InputStream documentContent) {
//        try {
//            return (Map<String, Object>) new ObjectMapper().readValue(documentContent, Map.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public Map<String, Object> parseYaml(InputStream documentContent) {
//        return (Map<String, Object>) new Yaml().load(documentContent);
//    }
//
//}
