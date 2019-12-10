package com.dxc.soporte.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.dxc.soporte.model.TicketModel;
import com.dxc.soporte.service.ElasticService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Service("elasticServiceImpl")
public class ElasticServiceImpl implements ElasticService {
	
	private static final Log LOGGER = LogFactory.getLog(ElasticServiceImpl.class);

	@Override
	public List<TicketModel> listRecomendedTickets(String titulo) throws IOException {
		// -------------------------
		//Conexion con ElasticSearch
		RestHighLevelClient client = new RestHighLevelClient(
		RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9300, "http")));
		//Creacion lista receptora vacia de tickets recomendados
		List<TicketModel> ListaRecomendacionesRecibida = new ArrayList<TicketModel>();
		
		LOGGER.info("Cliente conectado. ");
		
		//recibimos un string titulo que debemos utilizar en la query a entregar a elastic
		//de entrada dejamos esto para mas tarde y ahora añadimos simplemente un get all o algo parecido, para ver si componemos bien el resultado recibido de elastic
			
		 
		/*
		 * MainResponse response = client.info(RequestOptions.DEFAULT); ClusterName
		 * clusterName = response.getClusterName(); String clusterUuid =
		 * response.getClusterUuid(); String nodeName = response.getNodeName(); Version
		 * version = response.getVersion(); Build build = response.getBuild();
		 * 
		 * LOGGER.info("Información del cluster: ");
		 * 
		 * LOGGER.info("Nombre del cluster: {}"+ clusterName.value());
		 * LOGGER.info("Identificador del cluster: {}"+ clusterUuid);
		 * LOGGER.info("Nombre de los nodos del cluster: {}"+ nodeName);
		 * LOGGER.info("Versión de elasticsearch del cluster: {}"+ version.toString());
		 * LOGGER.info("build de elasticsearch del cluster: {}"+ build.toString());
		 */
		//Creamos una peticion de busqueda sobre el indice tick
		SearchRequest searchRequest = new SearchRequest("tick");//la busqueda se realiza unicamente sobre e lindice determinado
		
		//Se prepara un constructor de busqueda
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		
		
		//creamos la query basica
		//MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", titulo);
		//searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		
		//creamos la query mas compleja
		
		  QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title",titulo)
		  //.fuzziness(Fuzziness.AUTO)
		  .fuzziness(2)
		  .prefixLength(3)
		  .maxExpansions(10) 		  
		  .analyzer("tick_analyzer");
		  searchSourceBuilder.query(matchQueryBuilder);
		 
		LOGGER.info("titulo a buscar: "+ titulo);
		//searchSourceBuilder.query(QueryBuilders.termQuery("title", titulo)); 
		searchSourceBuilder.from(0); //determines the result index to start searching from. Defaults to 0.
		searchSourceBuilder.size(5); //numero de recomendaciones coincidentes devueltas
		//searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));//timeout that controls how long the search is allowed to take
		searchRequest.source(searchSourceBuilder);
		
		
		//Búsqueda de datos
		//SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		
		//SearchRequest searchRequest = new
		//SearchRequest("tick").source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			//searchResponse = client.search(searchRequest);
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
		}catch (IOException e) {
			e.printStackTrace();
			
		}
		SearchHits searchHits = searchResponse.getHits();
		Map<?, ?> hitMap;
		JsonElement jsonElement;
		TicketModel ticket_recomendado;
		Gson gson=new Gson();
		
		for(SearchHit hit : searchHits) {
			hitMap = hit.getSourceAsMap();
			jsonElement = gson.toJsonTree(hitMap);
			ticket_recomendado = gson.fromJson(jsonElement, TicketModel.class);
			LOGGER.info("titulo ticket recomendado: "+ ticket_recomendado.getTitle());
			LOGGER.info("solucion: "+ ticket_recomendado.getResolution());
			ListaRecomendacionesRecibida.add(ticket_recomendado);
		}
		 
		client.close();
		LOGGER.info("Cliente desconectado.");
		
		//---------------------------------------
		System.out.println("ticket info");
		
		
		
		//---------------------------------
		return ListaRecomendacionesRecibida;
	}

}
