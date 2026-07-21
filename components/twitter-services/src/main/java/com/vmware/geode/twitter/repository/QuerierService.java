package com.vmware.geode.twitter.repository;

import java.util.Collection;

public interface QuerierService {
    <T> Collection<T> query(String avgPolarityQuery);
}
