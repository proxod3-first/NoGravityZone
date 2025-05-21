package com.proxod3.nogravityzone.ui.screens.create_workout

import com.proxod3.nogravityzone.ui.models.BodyPart
import com.proxod3.nogravityzone.ui.models.Equipment
import com.proxod3.nogravityzone.ui.models.Exercise
import com.proxod3.nogravityzone.ui.models.TargetMuscle
import com.proxod3.nogravityzone.ui.models.workout.Workout
import com.proxod3.nogravityzone.ui.models.workout.WorkoutExercise

// UI States
sealed interface CreateWorkoutAction {
    object NavigateBack : CreateWorkoutAction
    data class NavigateToExercise(val exercise: Exercise) : CreateWorkoutAction
    object ShowFilterSheet : CreateWorkoutAction
    object NavigateToFeed : CreateWorkoutAction
    object UploadWorkout : CreateWorkoutAction
    data class SearchQueryChanged(val query: String) : CreateWorkoutAction
    data class EditWorkout(val workout: Workout) : CreateWorkoutAction
    data class ExerciseSelected(val exercise: Exercise) : CreateWorkoutAction
    data class ExerciseModified(val exercise: WorkoutExercise) : CreateWorkoutAction
    data class ExerciseDeleted(val exercise: WorkoutExercise) : CreateWorkoutAction
    object DismissAddExerciseDialog : CreateWorkoutAction
    object ApplyFilters : CreateWorkoutAction
    object ClearFilters : CreateWorkoutAction
    object HideFilterSheet : CreateWorkoutAction

    data class AddWorkoutExercise(val exercise: WorkoutExercise?) : CreateWorkoutAction
    data class EditWorkoutExercise(val exercise: WorkoutExercise?) : CreateWorkoutAction
    data class BodyPartFilterChange(val bodyPart: BodyPart) : CreateWorkoutAction
    data class EquipmentFilterChange(val equipment: Equipment) : CreateWorkoutAction
    data class TargetMuscleFilterChange(val targetMuscle: TargetMuscle) : CreateWorkoutAction
    object ToggleExerciseWorkoutListShow : CreateWorkoutAction

}