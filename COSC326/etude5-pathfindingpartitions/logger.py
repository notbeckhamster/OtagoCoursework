"""Logger for Etude 5 PathFinding Partitions
Author: Beckham Wilson 7564267 wilbe447
"""
import logging
import logging.handlers


class Logger:
    """
    Logger class is used to log messages.
    """

    logger = logging.getLogger(__name__)
    logger.setLevel(logging.DEBUG)

    formatter = logging.Formatter(
        "Logging: %(asctime)s | %(filename)s |  %(funcName)s() | "
        "line: %(lineno)d | %(levelname)s | %(message)s"
    )

    file_handler = logging.FileHandler("pathingfinding_partition.log")
    file_handler.setLevel(logging.DEBUG)
    file_handler.setFormatter(formatter)

    logger.addHandler(file_handler)