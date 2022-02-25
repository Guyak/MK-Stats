package fr.harmoniamk.statsmk.extension

import fr.harmoniamk.statsmk.database.model.War

fun List<War>.getLasts(teamId: String?) = this.filter { war -> war.isOver && war.teamHost == teamId }.sortedByDescending{ it.createdDate }
fun List<War>.getCurrent(teamId: String?) = this.singleOrNull { war -> !war.isOver && war.teamHost == teamId }
fun List<War>.getBests(teamId: String?) = this.filter { war -> war.isOver && war.teamHost == teamId  }.sortedWith(compareBy<War> { it.scoreHost }.thenBy { it.displayedAverage.toInt() }).reversed()