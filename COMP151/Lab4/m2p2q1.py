def percent_hour(current_minute: int) -> int:
    return int((current_minute/60.0)*100)

print(percent_hour(30))