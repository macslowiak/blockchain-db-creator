package com.cryptosearcher.client.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class BlockchainClientConfiguration {

    private String url;

}
