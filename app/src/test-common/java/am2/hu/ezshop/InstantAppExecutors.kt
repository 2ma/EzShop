package am2.hu.ezshop

import java.util.concurrent.Executor


class InstantAppExecutors : AppExecutors(Executor { command -> command?.run() })