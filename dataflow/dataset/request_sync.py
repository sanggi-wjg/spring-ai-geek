import requests
from thread_manager import ThreadManager, ThreadArgument


def get_countries():
    results = []
    params = {"page": 1, "alpha2s": ["DK", "US"]}

    while True:
        response = requests.get(url="http://localhost:8090/api/v1/countries", params=params)
        response.raise_for_status()
        resp = response.json()

        results.extend(resp["items"])
        params["page"] += 1

        if not resp["page"]["hasNext"]:
            break

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

            # for s in stats_requests:
            #     sync(s)
            manager = ThreadManager(
                sync,
                [ThreadArgument(thread_name=f"Thread:{s}", args=(s,)) for s in stats_requests],
            )
            manager.run()


def sync(stat_request):
    response = requests.put(f"http://localhost:8090/api/v1/trade-stats-requests/{stat_request['id']}/sync")
    print(stat_request, response.status_code, response.text)


sync_trade_stats()
