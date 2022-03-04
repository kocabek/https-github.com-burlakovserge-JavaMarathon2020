/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.graphql.test.tester;


import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.graphql.GraphQlRequest;
import org.springframework.graphql.web.WebGraphQlHandler;
import org.springframework.graphql.web.WebInput;
import org.springframework.graphql.web.WebOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;


/**
 * {@code GraphQlTransport} that calls directly a {@link WebGraphQlHandler}.
 *
 * @author Rossen Stoyanchev
 * @since 1.0.0
 */
final class WebGraphQlHandlerTransport extends AbstractDirectTransport {

	private final URI url;

	private final HttpHeaders headers = new HttpHeaders();

	private final WebGraphQlHandler graphQlHandler;


	WebGraphQlHandlerTransport(@Nullable URI url, HttpHeaders headers, WebGraphQlHandler graphQlHandler) {
		this.url = (url != null ? url : URI.create(""));
		this.headers.addAll(headers);
		this.graphQlHandler = graphQlHandler;
	}


	public URI getUrl() {
		return this.url;
	}

	public HttpHeaders getHeaders() {
		return this.headers;
	}

	public WebGraphQlHandler getGraphQlHandler() {
		return this.graphQlHandler;
	}


	@Override
	protected Mono<WebOutput> executeInternal(GraphQlRequest request) {
		return this.graphQlHandler.handleRequest(
				new WebInput(this.url, this.headers, request.toMap(), idGenerator.generateId().toString(), null));
	}

}
