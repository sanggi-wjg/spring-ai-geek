import requests
from thread_manager import ThreadManager, ThreadArgument


def get_countries():
    results = []
    params = {"page": 8, "size": 10}
    # params = {"page": 1, "alpha2s": ['HK', 'HU', 'IN', 'ID', 'IL', 'IT', 'JM', 'JP']}
    # 'HK', 'HU', 'IN', 'ID', 'IL', 'IT', 'JM', 'JP'
    # 'BE', 'BA', 'BR', 'BN', 'BG', 'BI', 'KH', 'CM', 'CA', 'CL', 'CN', 'CO', 'CG', 'HR', 'CU', 'CZ', 'DK', 'EC', 'EG', 'FR', 'GE', 'DE', 'GR', 'GD'
    # 'NL', 'NZ', 'NO', 'PY', 'PE', 'PH', 'PL', 'PT', 'PR', 'QA', 'RO', 'RU', 'SA', 'SN', 'RS', 'SG', 'ZA', 'ES', 'SE', 'CH', 'SY', 'TW', 'TR', 'UA', 'AE', 'GB', 'US', 'UY', 'UZ', 'VE', 'VN'

    # while True:
    #     response = requests.get(url="http://localhost:8090/api/v1/countries", params=params)
    #     response.raise_for_status()
    #     resp = response.json()
    #
    #     results.extend(resp["items"])
    #     params["page"] += 1
    #
    #     if not resp["page"]["hasNext"]:
    #         break

    response = requests.get(url="http://localhost:8090/api/v1/countries", params=params)
    response.raise_for_status()
    resp = response.json()
    results.extend(resp["items"])

    return results


def sync_trade_stats():
    countries = get_countries()

    for country in countries:
        stats_requests = []
        params = {"isSynced": False, "page": 1}

        while True:
            response = requests.get(
                url=f"http://localhost:8090/api/v1/countries/{country['id']}/trade-stats-requests",
                params=params,
            )
            response.raise_for_status()
            resp = response.json()

            stats_requests.extend(resp["items"])
            params["page"] += 1

            if not resp["page"]["hasNext"]:
                break

        for s in stats_requests:
            sync(s, country)

        # manager = ThreadManager(
        #     callable_func=sync,
        #     thread_arguments=[ThreadArgument(thread_name=f"Thread:{s}", args=(s, country)) for s in stats_requests],
        # )
        # manager.run()


def sync(stat_request, country):
    response = requests.put(f"http://localhost:8090/api/v1/trade-stats-requests/{stat_request['id']}/sync")
    print(country, stat_request, response.status_code, response.text)


sync_trade_stats()
