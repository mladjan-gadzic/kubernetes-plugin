package io.armadaproject;

import api.SubmitOuterClass.JobSubmitRequest;
import api.SubmitOuterClass.JobSubmitRequest.Builder;
import api.SubmitOuterClass.JobSubmitRequestItem;
import io.fabric8.kubernetes.api.model.Pod;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import k8s.io.api.core.v1.Generated.AWSElasticBlockStoreVolumeSource;
import k8s.io.api.core.v1.Generated.Affinity;
import k8s.io.api.core.v1.Generated.Capabilities;
import k8s.io.api.core.v1.Generated.ConfigMapEnvSource;
import k8s.io.api.core.v1.Generated.ConfigMapKeySelector;
import k8s.io.api.core.v1.Generated.Container;
import k8s.io.api.core.v1.Generated.ContainerPort;
import k8s.io.api.core.v1.Generated.ContainerResizePolicy;
import k8s.io.api.core.v1.Generated.EmptyDirVolumeSource;
import k8s.io.api.core.v1.Generated.EnvFromSource;
import k8s.io.api.core.v1.Generated.EnvVar;
import k8s.io.api.core.v1.Generated.EnvVarSource;
import k8s.io.api.core.v1.Generated.EphemeralContainer;
import k8s.io.api.core.v1.Generated.EphemeralContainerCommon;
import k8s.io.api.core.v1.Generated.ExecAction;
import k8s.io.api.core.v1.Generated.FlexVolumeSource;
import k8s.io.api.core.v1.Generated.GCEPersistentDiskVolumeSource;
import k8s.io.api.core.v1.Generated.GRPCAction;
import k8s.io.api.core.v1.Generated.GitRepoVolumeSource;
import k8s.io.api.core.v1.Generated.GlusterfsVolumeSource;
import k8s.io.api.core.v1.Generated.HTTPGetAction;
import k8s.io.api.core.v1.Generated.HTTPHeader;
import k8s.io.api.core.v1.Generated.HostAlias;
import k8s.io.api.core.v1.Generated.HostPathVolumeSource;
import k8s.io.api.core.v1.Generated.ISCSIVolumeSource;
import k8s.io.api.core.v1.Generated.KeyToPath;
import k8s.io.api.core.v1.Generated.Lifecycle;
import k8s.io.api.core.v1.Generated.LifecycleHandler;
import k8s.io.api.core.v1.Generated.LocalObjectReference;
import k8s.io.api.core.v1.Generated.NFSVolumeSource;
import k8s.io.api.core.v1.Generated.NodeAffinity;
import k8s.io.api.core.v1.Generated.NodeSelector;
import k8s.io.api.core.v1.Generated.NodeSelectorRequirement;
import k8s.io.api.core.v1.Generated.NodeSelectorTerm;
import k8s.io.api.core.v1.Generated.ObjectFieldSelector;
import k8s.io.api.core.v1.Generated.PersistentVolumeClaimVolumeSource;
import k8s.io.api.core.v1.Generated.PodAffinity;
import k8s.io.api.core.v1.Generated.PodAffinityTerm;
import k8s.io.api.core.v1.Generated.PodAntiAffinity;
import k8s.io.api.core.v1.Generated.PodDNSConfig;
import k8s.io.api.core.v1.Generated.PodDNSConfigOption;
import k8s.io.api.core.v1.Generated.PodOS;
import k8s.io.api.core.v1.Generated.PodReadinessGate;
import k8s.io.api.core.v1.Generated.PodResourceClaim;
import k8s.io.api.core.v1.Generated.PodSchedulingGate;
import k8s.io.api.core.v1.Generated.PodSecurityContext;
import k8s.io.api.core.v1.Generated.PodSpec;
import k8s.io.api.core.v1.Generated.PreferredSchedulingTerm;
import k8s.io.api.core.v1.Generated.Probe;
import k8s.io.api.core.v1.Generated.ProbeHandler;
import k8s.io.api.core.v1.Generated.RBDVolumeSource;
import k8s.io.api.core.v1.Generated.ResourceFieldSelector;
import k8s.io.api.core.v1.Generated.ResourceRequirements;
import k8s.io.api.core.v1.Generated.SELinuxOptions;
import k8s.io.api.core.v1.Generated.SeccompProfile;
import k8s.io.api.core.v1.Generated.SecretEnvSource;
import k8s.io.api.core.v1.Generated.SecretKeySelector;
import k8s.io.api.core.v1.Generated.SecretVolumeSource;
import k8s.io.api.core.v1.Generated.SecurityContext;
import k8s.io.api.core.v1.Generated.SleepAction;
import k8s.io.api.core.v1.Generated.Sysctl;
import k8s.io.api.core.v1.Generated.TCPSocketAction;
import k8s.io.api.core.v1.Generated.Toleration;
import k8s.io.api.core.v1.Generated.TopologySpreadConstraint;
import k8s.io.api.core.v1.Generated.Volume;
import k8s.io.api.core.v1.Generated.VolumeDevice;
import k8s.io.api.core.v1.Generated.VolumeMount;
import k8s.io.api.core.v1.Generated.VolumeSource;
import k8s.io.api.core.v1.Generated.WeightedPodAffinityTerm;
import k8s.io.api.core.v1.Generated.WindowsSecurityContextOptions;
import k8s.io.apimachinery.pkg.api.resource.Generated.Quantity;
import k8s.io.apimachinery.pkg.apis.meta.v1.Generated.LabelSelector;
import k8s.io.apimachinery.pkg.apis.meta.v1.Generated.LabelSelectorRequirement;
import k8s.io.apimachinery.pkg.util.intstr.Generated.IntOrString;

public class ArmadaMapper {

  private final String queue;
  private final String jobSetId;
  private final Pod pod;

  public ArmadaMapper(String queue, String jobSetId, Pod pod) {
    this.queue = queue;
    this.jobSetId = jobSetId;
    this.pod = pod;
  }

  public JobSubmitRequest createJobSubmitRequest() {
    Builder builder = JobSubmitRequest.newBuilder();

    builder
        .setQueue(queue)
        .setJobSetId(jobSetId);

    builder.addJobRequestItems(createJobRequestItems(pod));

    return builder.build();
  }

  private JobSubmitRequestItem createJobRequestItems(Pod pod) {
    // priority skipped
    // client_id skipped
    // required_node_labels deprecated
    // pod_spec deprecated
    // ingress skipped
    // services skipped
    // scheduler skipped
    JobSubmitRequestItem.Builder builder = JobSubmitRequestItem.newBuilder();

    if (Objects.nonNull(pod.getMetadata()) && Objects.nonNull(pod.getMetadata().getNamespace())) {
      builder.setNamespace(pod.getMetadata().getNamespace());
    } else {
      builder.setNamespace("default");
    }

    if (Objects.nonNull(pod.getMetadata()) && !pod.getMetadata().getLabels().isEmpty()) {
      builder.putAllLabels(pod.getMetadata().getLabels());
    }

    if (Objects.nonNull(pod.getMetadata()) && !pod.getMetadata().getAnnotations().isEmpty()) {
      builder.putAllAnnotations(pod.getMetadata().getAnnotations());
    }

    if (Objects.nonNull(pod.getSpec())) {
      builder.addPodSpecs(mapPodSpec(pod));
    }

    return builder.build();
  }

  private PodSpec mapPodSpec(Pod pod) {
    PodSpec.Builder builder = PodSpec.newBuilder();

    if (Objects.isNull(pod.getSpec())) {
      return builder.build();
    }

    io.fabric8.kubernetes.api.model.PodSpec podSpec = pod.getSpec();

    List<io.fabric8.kubernetes.api.model.Volume> volumes = podSpec.getVolumes();
    if (Objects.nonNull(volumes) && !volumes.isEmpty()) {
      builder.addAllVolumes(mapVolumes(volumes));
    }

    List<io.fabric8.kubernetes.api.model.Container> containers = podSpec.getContainers();
    if (Objects.nonNull(containers) && !containers.isEmpty()) {
      builder.addAllContainers(mapContainers(containers));
    }

    builder
        // initContainers skipped
        .addAllEphemeralContainers(mapEphermalContainers(pod))
        .setRestartPolicy(pod.getSpec().getRestartPolicy())
        .setTerminationGracePeriodSeconds(pod.getSpec().getTerminationGracePeriodSeconds())
        .setActiveDeadlineSeconds(pod.getSpec().getActiveDeadlineSeconds())
        .setDnsPolicy(pod.getSpec().getDnsPolicy())
        .putAllNodeSelector(pod.getSpec().getNodeSelector())
        .setServiceAccount(pod.getSpec().getServiceAccount())
        .setAutomountServiceAccountToken(pod.getSpec().getAutomountServiceAccountToken())
        .setNodeName(pod.getSpec().getNodeName())
        .setHostNetwork(pod.getSpec().getHostNetwork())
        .setHostPID(pod.getSpec().getHostPID())
        .setHostIPC(pod.getSpec().getHostIPC())
        .setShareProcessNamespace(pod.getSpec().getShareProcessNamespace())
        .setSecurityContext(mapPodSecurityContext(pod.getSpec().getSecurityContext()))
        .addAllImagePullSecrets(mapLocalObjectReference(pod.getSpec().getImagePullSecrets()))
        .setHostname(pod.getSpec().getHostname())
        .setSubdomain(pod.getSpec().getSubdomain())
        .setAffinity(mapAffinity(pod.getSpec().getAffinity()))
        .setSchedulerName(pod.getSpec().getSchedulerName())
        .addAllTolerations(mapTolerations(pod.getSpec().getTolerations()))
        .addAllHostAliases(mapHostAliases(pod.getSpec().getHostAliases()))
        .setPriorityClassName(pod.getSpec().getPriorityClassName())
        .setPriority(pod.getSpec().getPriority())
        .setDnsConfig(mapPodDnsConfig(pod.getSpec().getDnsConfig()))
        .addAllReadinessGates(mapPodReadinessGate(pod.getSpec().getReadinessGates()))
        .setRuntimeClassName(pod.getSpec().getRuntimeClassName())
        .setEnableServiceLinks(pod.getSpec().getEnableServiceLinks())
        .setPreemptionPolicy(pod.getSpec().getPreemptionPolicy())
        .putAllOverhead(mapOvearhead(pod.getSpec().getOverhead()))
        .addAllTopologySpreadConstraints(
            mapTopologySpreadConstraints(pod.getSpec().getTopologySpreadConstraints()))
        .setSetHostnameAsFQDN(pod.getSpec().getSetHostnameAsFQDN())
        .setOs(mapPodOs(pod.getSpec().getOs()))
        .setHostUsers(pod.getSpec().getHostUsers())
        .addAllSchedulingGates(mapPodSchedulingGates(pod.getSpec().getSchedulingGates()))
        .addAllResourceClaims(mapPodResourceClaims(pod.getSpec().getResourceClaims()));
    return builder.build();
  }

  private Iterable<? extends PodResourceClaim> mapPodResourceClaims(
      List<io.fabric8.kubernetes.api.model.PodResourceClaim> resourceClaims) {
    return resourceClaims.stream()
        .map(r -> PodResourceClaim.newBuilder()
            .setName(r.getName())
            // FIXME possible mismatch
            .setResourceClaimName(r.getName())
            // FIXME possible mismatch
            .setResourceClaimTemplateName(r.getName())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends PodSchedulingGate> mapPodSchedulingGates(
      List<io.fabric8.kubernetes.api.model.PodSchedulingGate> schedulingGates) {
    return schedulingGates.stream()
        .map(g -> PodSchedulingGate.newBuilder()
            .setName(g.getName())
            .build())
        .collect(Collectors.toList());
  }

  private PodOS mapPodOs(io.fabric8.kubernetes.api.model.PodOS os) {
    return PodOS.newBuilder()
        .setName(os.getName())
        .build();
  }

  private Iterable<? extends TopologySpreadConstraint> mapTopologySpreadConstraints(
      List<io.fabric8.kubernetes.api.model.TopologySpreadConstraint> topologySpreadConstraints) {
    return topologySpreadConstraints.stream()
        .map(c -> TopologySpreadConstraint.newBuilder()
            .setMaxSkew(c.getMaxSkew())
            .setTopologyKey(c.getTopologyKey())
            .setWhenUnsatisfiable(c.getWhenUnsatisfiable())
            .setLabelSelector(mapLabelSelector(c.getLabelSelector()))
            .setMinDomains(c.getMinDomains())
            .setNodeAffinityPolicy(c.getNodeAffinityPolicy())
            .setNodeTaintsPolicy(c.getNodeTaintsPolicy())
            .addAllMatchLabelKeys(c.getMatchLabelKeys())
            .build())
        .collect(Collectors.toList());
  }

  private Map<String, Quantity> mapOvearhead(
      Map<String, io.fabric8.kubernetes.api.model.Quantity> overhead) {
    return overhead.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey,
            e -> Quantity.newBuilder().setString(e.getValue().getAmount()).build()));
  }

  private Iterable<? extends PodReadinessGate> mapPodReadinessGate(
      List<io.fabric8.kubernetes.api.model.PodReadinessGate> readinessGates) {
    return readinessGates.stream()
        .map(g -> PodReadinessGate.newBuilder()
            .setConditionType(g.getConditionType())
            .build())
        .collect(Collectors.toList());
  }

  private PodDNSConfig mapPodDnsConfig(io.fabric8.kubernetes.api.model.PodDNSConfig dnsConfig) {
    return PodDNSConfig.newBuilder()
        .addAllNameservers(dnsConfig.getNameservers())
        .addAllSearches(dnsConfig.getSearches())
        .addAllOptions(mapPodDNSConfigOption(dnsConfig.getOptions()))
        .build();
  }

  private Iterable<? extends PodDNSConfigOption> mapPodDNSConfigOption(
      List<io.fabric8.kubernetes.api.model.PodDNSConfigOption> options) {
    return options.stream()
        .map(o -> PodDNSConfigOption.newBuilder()
            .setName(o.getName())
            .setValue(o.getValue())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends HostAlias> mapHostAliases(
      List<io.fabric8.kubernetes.api.model.HostAlias> hostAliases) {
    return hostAliases.stream()
        .map(h -> HostAlias.newBuilder()
            .setIp(h.getIp())
            .addAllHostnames(h.getHostnames())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends Toleration> mapTolerations(
      List<io.fabric8.kubernetes.api.model.Toleration> tolerations) {
    return tolerations.stream()
        .map(t -> Toleration.newBuilder()
            .setKey(t.getKey())
            .setOperator(t.getOperator())
            .setValue(t.getValue())
            .setEffect(t.getEffect())
            .setTolerationSeconds(t.getTolerationSeconds())
            .build())
        .collect(Collectors.toList());
  }

  private Affinity mapAffinity(io.fabric8.kubernetes.api.model.Affinity affinity) {
    return Affinity.newBuilder()
        .setNodeAffinity(mapNodeAffinity(affinity.getNodeAffinity()))
        .setPodAffinity(mapPodAffinity(affinity.getPodAffinity()))
        .setPodAntiAffinity(mapPodAntiAffinity(affinity.getPodAntiAffinity()))
        .build();
  }

  private PodAntiAffinity mapPodAntiAffinity(
      io.fabric8.kubernetes.api.model.PodAntiAffinity podAntiAffinity) {
    return PodAntiAffinity.newBuilder()
        .addAllRequiredDuringSchedulingIgnoredDuringExecution(
            mapPodAffinityTerm(podAntiAffinity.getRequiredDuringSchedulingIgnoredDuringExecution()))
        .addAllPreferredDuringSchedulingIgnoredDuringExecution(
            mapWeightedPodAffinityTerm(
                podAntiAffinity.getPreferredDuringSchedulingIgnoredDuringExecution()))
        .build();
  }

  private PodAffinity mapPodAffinity(io.fabric8.kubernetes.api.model.PodAffinity podAffinity) {
    return PodAffinity.newBuilder()
        .addAllRequiredDuringSchedulingIgnoredDuringExecution(
            mapPodAffinityTerm(podAffinity.getRequiredDuringSchedulingIgnoredDuringExecution()))
        .addAllPreferredDuringSchedulingIgnoredDuringExecution(
            mapWeightedPodAffinityTerm(
                podAffinity.getPreferredDuringSchedulingIgnoredDuringExecution()))
        .build();
  }

  private Iterable<? extends WeightedPodAffinityTerm> mapWeightedPodAffinityTerm(
      List<io.fabric8.kubernetes.api.model.WeightedPodAffinityTerm> preferredDuringSchedulingIgnoredDuringExecution) {
    return preferredDuringSchedulingIgnoredDuringExecution.stream()
        .map(t -> WeightedPodAffinityTerm.newBuilder()
            .setWeight(t.getWeight())
            .setPodAffinityTerm(mapPodAffinityTerm(t.getPodAffinityTerm()))
            .build())
        .collect(Collectors.toList());
  }

  private PodAffinityTerm mapPodAffinityTerm(
      io.fabric8.kubernetes.api.model.PodAffinityTerm podAffinityTerm) {
    return PodAffinityTerm.newBuilder()
        .setLabelSelector(mapLabelSelector(podAffinityTerm.getLabelSelector()))
        .addAllNamespaces(podAffinityTerm.getNamespaces())
        .setTopologyKey(podAffinityTerm.getTopologyKey())
        .setNamespaceSelector(mapLabelSelector(podAffinityTerm.getNamespaceSelector()))
        .addAllMatchLabelKeys(podAffinityTerm.getMatchLabelKeys())
        .addAllMismatchLabelKeys(podAffinityTerm.getMismatchLabelKeys())
        .build();
  }

  private Iterable<? extends PodAffinityTerm> mapPodAffinityTerm(
      List<io.fabric8.kubernetes.api.model.PodAffinityTerm> requiredDuringSchedulingIgnoredDuringExecution) {
    return requiredDuringSchedulingIgnoredDuringExecution.stream()
        .map(t -> PodAffinityTerm.newBuilder()
            .setLabelSelector(mapLabelSelector(t.getLabelSelector()))
            .addAllNamespaces(t.getNamespaces())
            .setTopologyKey(t.getTopologyKey())
            .setNamespaceSelector(mapLabelSelector(t.getNamespaceSelector()))
            .addAllMatchLabelKeys(t.getMatchLabelKeys())
            .addAllMismatchLabelKeys(t.getMismatchLabelKeys())
            .build())
        .collect(Collectors.toList());
  }

  private LabelSelector mapLabelSelector(
      io.fabric8.kubernetes.api.model.LabelSelector labelSelector) {
    return LabelSelector.newBuilder()
        .putAllMatchLabels(labelSelector.getMatchLabels())
        .addAllMatchExpressions(mapLabelSelectorRequirement(labelSelector.getMatchExpressions()))
        .build();
  }

  private Iterable<? extends LabelSelectorRequirement> mapLabelSelectorRequirement(
      List<io.fabric8.kubernetes.api.model.LabelSelectorRequirement> matchExpressions) {
    return matchExpressions.stream()
        .map(r -> LabelSelectorRequirement.newBuilder()
            .setKey(r.getKey())
            .setOperator(r.getOperator())
            .addAllValues(r.getValues())
            .build())
        .collect(Collectors.toList());
  }

  private NodeAffinity mapNodeAffinity(io.fabric8.kubernetes.api.model.NodeAffinity nodeAffinity) {
    return NodeAffinity.newBuilder()
        .setRequiredDuringSchedulingIgnoredDuringExecution(
            mapNodeSelector(nodeAffinity.getRequiredDuringSchedulingIgnoredDuringExecution()))
        .addAllPreferredDuringSchedulingIgnoredDuringExecution(
            mapPreferredSchedulingTerms(
                nodeAffinity.getPreferredDuringSchedulingIgnoredDuringExecution()))
        .build();
  }

  private Iterable<? extends PreferredSchedulingTerm> mapPreferredSchedulingTerms(
      List<io.fabric8.kubernetes.api.model.PreferredSchedulingTerm> preferredDuringSchedulingIgnoredDuringExecution) {
    return preferredDuringSchedulingIgnoredDuringExecution.stream()
        .map(t -> PreferredSchedulingTerm.newBuilder()
            .setWeight(t.getWeight())
            .setPreference(mapNodeSelector(t.getPreference()))
            .build())
        .collect(Collectors.toList());
  }

  private NodeSelectorTerm mapNodeSelector(
      io.fabric8.kubernetes.api.model.NodeSelectorTerm preference) {
    return NodeSelectorTerm.newBuilder()
        .addAllMatchExpressions(mapNodeSelectorRequirement(preference.getMatchExpressions()))
        .addAllMatchFields(mapNodeSelectorRequirement(preference.getMatchFields()))
        .build();
  }

  private NodeSelector mapNodeSelector(
      io.fabric8.kubernetes.api.model.NodeSelector requiredDuringSchedulingIgnoredDuringExecution) {
    return NodeSelector.newBuilder()
        .addAllNodeSelectorTerms(mapNodeSelectorTerms(
            requiredDuringSchedulingIgnoredDuringExecution.getNodeSelectorTerms()))
        .build();
  }

  private Iterable<? extends NodeSelectorTerm> mapNodeSelectorTerms(
      List<io.fabric8.kubernetes.api.model.NodeSelectorTerm> nodeSelectorTerms) {
    return nodeSelectorTerms.stream()
        .map(t -> NodeSelectorTerm.newBuilder()
            .addAllMatchExpressions(mapNodeSelectorRequirement(t.getMatchExpressions()))
            .addAllMatchFields(mapNodeSelectorRequirement(t.getMatchFields()))
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends NodeSelectorRequirement> mapNodeSelectorRequirement(
      List<io.fabric8.kubernetes.api.model.NodeSelectorRequirement> matchExpressions) {
    return matchExpressions.stream()
        .map(r -> NodeSelectorRequirement.newBuilder()
            .setKey(r.getKey())
            .setOperator(r.getOperator())
            .addAllValues(r.getValues())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends LocalObjectReference> mapLocalObjectReference(
      List<io.fabric8.kubernetes.api.model.LocalObjectReference> imagePullSecrets) {
    return imagePullSecrets.stream()
        .map(r -> LocalObjectReference.newBuilder()
            .setName(r.getName())
            .build())
        .collect(Collectors.toList());
  }

  private PodSecurityContext mapPodSecurityContext(
      io.fabric8.kubernetes.api.model.PodSecurityContext securityContext) {
    return PodSecurityContext.newBuilder()
        .setSeLinuxOptions(mapSeLinuxOptions(securityContext.getSeLinuxOptions()))
        .setWindowsOptions(mapWindowsOptions(securityContext.getWindowsOptions()))
        .setRunAsUser(securityContext.getRunAsUser())
        .setRunAsGroup(securityContext.getRunAsGroup())
        .setRunAsNonRoot(securityContext.getRunAsNonRoot())
        .addAllSupplementalGroups(securityContext.getSupplementalGroups())
        // FIXME mismatch
        // .setSupplementalGroupsPolicy()
        .setFsGroup(securityContext.getFsGroup())
        .addAllSysctls(mapSysctls(securityContext.getSysctls()))
        .setFsGroupChangePolicy(securityContext.getFsGroupChangePolicy())
        .setSeccompProfile(mapSeccompProfile(securityContext.getSeccompProfile()))
        // FIXME mismatch
        // .setAppArmorProfile()
        .build();
  }

  private Iterable<? extends Sysctl> mapSysctls(
      List<io.fabric8.kubernetes.api.model.Sysctl> sysctls) {
    return sysctls.stream()
        .map(s -> Sysctl.newBuilder()
            .setName(s.getName())
            .setValue(s.getValue())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends EphemeralContainer> mapEphermalContainers(Pod pod) {
    return pod.getSpec().getEphemeralContainers().stream()
        .map(c -> EphemeralContainer.newBuilder()
            .setEphemeralContainerCommon(EphemeralContainerCommon.newBuilder()
                .setName(c.getName())
                .setImage(c.getImage())
                .addAllCommand(c.getCommand())
                .addAllArgs(c.getArgs())
                .setWorkingDir(c.getWorkingDir())
                .addAllPorts(mapContainerPorts(c.getPorts()))
                .addAllEnvFrom(mapEnvFromSource(c.getEnvFrom()))
                .addAllEnv(mapEnvVars(c.getEnv()))
                .setResources(mapResourceRequirements(c.getResources()))
                .addAllResizePolicy(mapContainerResizePolicy(c.getResizePolicy()))
                .setRestartPolicy(c.getRestartPolicy())
                .addAllVolumeMounts(mapVolumeMounts(c.getVolumeMounts()))
                .addAllVolumeDevices(mapVolumeDevices(c.getVolumeDevices()))
                .setLivenessProbe(mapProbe(c.getLivenessProbe()))
                .setReadinessProbe(mapProbe(c.getReadinessProbe()))
                .setStartupProbe(mapProbe(c.getStartupProbe()))
                .setLifecycle(mapLifecycle(c.getLifecycle()))
                .setTerminationMessagePath(c.getTerminationMessagePath())
                .setTerminationMessagePolicy(c.getTerminationMessagePolicy())
                .setImagePullPolicy(c.getImagePullPolicy())
                .setSecurityContext(mapSecurityContext(c.getSecurityContext()))
                .setStdin(c.getStdin())
                .setStdinOnce(c.getStdinOnce())
                .setTty(c.getTty())
                .build())
            .setTargetContainerName(c.getTargetContainerName())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends Container> mapContainers(
      List<io.fabric8.kubernetes.api.model.Container> containers) {
    return containers
        .stream()
        .map(c -> {
          Container.Builder builder = Container.newBuilder();

          String name = c.getName();
          if (Objects.nonNull(name)) {
            builder.setName(name);
          }

          String image = c.getImage();
          if (Objects.nonNull(image)) {
            builder.setImage(image);
          }

          List<String> command = c.getCommand();
          if (Objects.nonNull(command) && !command.isEmpty()) {
            builder.addAllCommand(command);
          }

          List<String> args = c.getArgs();
          if (Objects.nonNull(args) && !args.isEmpty()) {
            builder.addAllArgs(args);
          }

          String workingDir = c.getWorkingDir();
          if (Objects.nonNull(workingDir)) {
            builder.setWorkingDir(workingDir);
          }

          List<io.fabric8.kubernetes.api.model.ContainerPort> ports = c.getPorts();
          if (Objects.nonNull(ports) && !ports.isEmpty()) {
            builder.addAllPorts(mapContainerPorts(ports));
          }

          List<io.fabric8.kubernetes.api.model.EnvFromSource> envFrom = c.getEnvFrom();
          if (Objects.nonNull(envFrom) && !envFrom.isEmpty()) {
            builder.addAllEnvFrom(mapEnvFromSource(envFrom));
          }

          List<io.fabric8.kubernetes.api.model.EnvVar> env = c.getEnv();
          if (Objects.nonNull(env) && !env.isEmpty()) {
            builder.addAllEnv(mapEnvVars(env));
          }

          io.fabric8.kubernetes.api.model.ResourceRequirements resources = c.getResources();
          if (Objects.nonNull(resources)) {
            builder.setResources(mapResourceRequirements(resources));
          }

          List<io.fabric8.kubernetes.api.model.ContainerResizePolicy> resizePolicy =
              c.getResizePolicy();
          if (Objects.nonNull(resizePolicy) && !resizePolicy.isEmpty()) {
            builder.addAllResizePolicy(mapContainerResizePolicy(resizePolicy));
          }

          String restartPolicy = c.getRestartPolicy();
          if (Objects.nonNull(restartPolicy)) {
            builder.setRestartPolicy(restartPolicy);
          }

          List<io.fabric8.kubernetes.api.model.VolumeMount> volumeMounts = c.getVolumeMounts();
          if (Objects.nonNull(volumeMounts) && !volumeMounts.isEmpty()) {
            builder.addAllVolumeMounts(mapVolumeMounts(volumeMounts));
          }

          List<io.fabric8.kubernetes.api.model.VolumeDevice> volumeDevices = c.getVolumeDevices();
          if (Objects.nonNull(volumeDevices) && !volumeDevices.isEmpty()) {
            builder.addAllVolumeDevices(mapVolumeDevices(volumeDevices));
          }

          io.fabric8.kubernetes.api.model.Probe livenessProbe = c.getLivenessProbe();
          if (Objects.nonNull(livenessProbe)) {
            builder.setLivenessProbe(mapProbe(livenessProbe));
          }

          io.fabric8.kubernetes.api.model.Probe readinessProbe = c.getReadinessProbe();
          if (Objects.nonNull(readinessProbe)) {
            builder.setReadinessProbe(mapProbe(readinessProbe));
          }

          io.fabric8.kubernetes.api.model.Probe startupProbe = c.getStartupProbe();
          if (Objects.nonNull(startupProbe)) {
            builder.setStartupProbe(mapProbe(startupProbe));
          }

          io.fabric8.kubernetes.api.model.Lifecycle lifecycle = c.getLifecycle();
          if (Objects.nonNull(lifecycle)) {
            builder.setLifecycle(mapLifecycle(lifecycle));
          }

          String terminationMessagePath = c.getTerminationMessagePath();
          if (Objects.nonNull(terminationMessagePath)) {
            builder.setTerminationMessagePath(terminationMessagePath);
          }

          String terminationMessagePolicy = c.getTerminationMessagePolicy();
          if (Objects.nonNull(terminationMessagePolicy)) {
            builder.setTerminationMessagePolicy(terminationMessagePolicy);
          }

          String imagePullPolicy = c.getImagePullPolicy();
          if (Objects.nonNull(imagePullPolicy)) {
            builder.setImagePullPolicy(imagePullPolicy);
          }

          io.fabric8.kubernetes.api.model.SecurityContext securityContext = c.getSecurityContext();
          if (Objects.nonNull(securityContext)) {
            builder.setSecurityContext(mapSecurityContext(securityContext));
          }

          Boolean stdin = c.getStdin();
          if (Objects.nonNull(stdin)) {
            builder.setStdin(stdin);
          }

          Boolean stdinOnce = c.getStdinOnce();
          if (Objects.nonNull(stdinOnce)) {
            builder.setStdinOnce(stdinOnce);
          }

          Boolean tty = c.getTty();
          if (Objects.nonNull(tty)) {
            builder.setTty(tty);
          }

          return builder.build();
        })
        .collect(Collectors.toList());
  }

  private SecurityContext mapSecurityContext(
      io.fabric8.kubernetes.api.model.SecurityContext securityContext) {
    return SecurityContext.newBuilder()
        .setCapabilities(mapCapabilities(securityContext.getCapabilities()))
        .setPrivileged(securityContext.getPrivileged())
        .setSeLinuxOptions(mapSeLinuxOptions(securityContext.getSeLinuxOptions()))
        .setWindowsOptions(mapWindowsOptions(securityContext.getWindowsOptions()))
        .setRunAsUser(securityContext.getRunAsUser())
        .setRunAsGroup(securityContext.getRunAsGroup())
        .setRunAsNonRoot(securityContext.getRunAsNonRoot())
        .setReadOnlyRootFilesystem(securityContext.getReadOnlyRootFilesystem())
        .setAllowPrivilegeEscalation(securityContext.getAllowPrivilegeEscalation())
        .setProcMount(securityContext.getProcMount())
        .setSeccompProfile(mapSeccompProfile(securityContext.getSeccompProfile()))
        // FIXME mismatch
        // .setAppArmorProfile()
        .build();
  }

  private SeccompProfile mapSeccompProfile(
      io.fabric8.kubernetes.api.model.SeccompProfile seccompProfile) {
    return SeccompProfile.newBuilder()
        .setType(seccompProfile.getType())
        .setLocalhostProfile(seccompProfile.getLocalhostProfile())
        .build();
  }

  private WindowsSecurityContextOptions mapWindowsOptions(
      io.fabric8.kubernetes.api.model.WindowsSecurityContextOptions windowsOptions) {
    return WindowsSecurityContextOptions.newBuilder()
        .setGmsaCredentialSpec(windowsOptions.getGmsaCredentialSpec())
        .setGmsaCredentialSpec(windowsOptions.getGmsaCredentialSpec())
        .setRunAsUserName(windowsOptions.getRunAsUserName())
        .setHostProcess(windowsOptions.getHostProcess())
        .build();
  }

  private SELinuxOptions mapSeLinuxOptions(
      io.fabric8.kubernetes.api.model.SELinuxOptions seLinuxOptions) {
    return SELinuxOptions.newBuilder()
        .setUser(seLinuxOptions.getUser())
        .setRole(seLinuxOptions.getRole())
        .setType(seLinuxOptions.getType())
        .setLevel(seLinuxOptions.getLevel())
        .build();
  }

  private Capabilities mapCapabilities(io.fabric8.kubernetes.api.model.Capabilities capabilities) {
    return Capabilities.newBuilder()
        .addAllAdd(capabilities.getAdd())
        .addAllDrop(capabilities.getDrop())
        .build();
  }

  private Lifecycle mapLifecycle(io.fabric8.kubernetes.api.model.Lifecycle lifecycle) {
    return Lifecycle.newBuilder()
        .setPostStart(mapLifecycleHandler(lifecycle.getPostStart()))
        .setPreStop(mapLifecycleHandler(lifecycle.getPreStop()))
        .build();
  }

  private LifecycleHandler mapLifecycleHandler(
      io.fabric8.kubernetes.api.model.LifecycleHandler postStart) {
    return LifecycleHandler.newBuilder()
        .setExec(ExecAction.newBuilder()
            .addAllCommand(postStart.getExec().getCommand())
            .build())
        .setHttpGet(mapHttpGetAction(postStart.getHttpGet()))
        .setTcpSocket(mapTCPSocketAction(postStart.getTcpSocket()))
        .setSleep(SleepAction.newBuilder().setSeconds(postStart.getSleep().getSeconds()).build())
        .build();
  }

  private Probe mapProbe(io.fabric8.kubernetes.api.model.Probe livenessProbe) {
    return Probe.newBuilder()
        .setHandler(mapProbeHandler(livenessProbe))
        .setInitialDelaySeconds(livenessProbe.getInitialDelaySeconds())
        .setTimeoutSeconds(livenessProbe.getTimeoutSeconds())
        .setPeriodSeconds(livenessProbe.getPeriodSeconds())
        .setSuccessThreshold(livenessProbe.getSuccessThreshold())
        .setFailureThreshold(livenessProbe.getFailureThreshold())
        .setTerminationGracePeriodSeconds(livenessProbe.getTerminationGracePeriodSeconds())
        .build();
  }

  private ProbeHandler mapProbeHandler(io.fabric8.kubernetes.api.model.Probe livenessProbe) {
    io.fabric8.kubernetes.api.model.ExecAction exec = livenessProbe.getExec();
    return ProbeHandler.newBuilder()
        .setExec(ExecAction.newBuilder()
            .addAllCommand(exec.getCommand())
            .build())
        .setHttpGet(mapHttpGetAction(livenessProbe.getHttpGet()))
        .setTcpSocket(mapTCPSocketAction(livenessProbe.getTcpSocket()))
        .setGrpc(mapGRPCAction(livenessProbe.getGrpc()))
        .build();
  }

  private GRPCAction mapGRPCAction(io.fabric8.kubernetes.api.model.GRPCAction grpc) {
    return GRPCAction.newBuilder()
        .setPort(grpc.getPort())
        .setService(grpc.getService())
        .build();
  }

  private TCPSocketAction mapTCPSocketAction(
      io.fabric8.kubernetes.api.model.TCPSocketAction tcpSocket) {
    return TCPSocketAction.newBuilder()
        .setPort(mapIntOrString(tcpSocket.getPort()))
        .setHost(tcpSocket.getHost())
        .build();
  }

  private HTTPGetAction mapHttpGetAction(io.fabric8.kubernetes.api.model.HTTPGetAction httpGet) {
    return HTTPGetAction.newBuilder()
        .setPath(httpGet.getPath())
        .setPort(mapIntOrString(httpGet.getPort()))
        .setHost(httpGet.getHost())
        .setScheme(httpGet.getScheme())
        .addAllHttpHeaders(mapHttpHeaders(httpGet.getHttpHeaders()))
        .build();
  }

  private Iterable<? extends HTTPHeader> mapHttpHeaders(
      List<io.fabric8.kubernetes.api.model.HTTPHeader> httpHeaders) {
    return httpHeaders.stream()
        .map(h -> HTTPHeader.newBuilder()
            .setName(h.getName())
            .setValue(h.getValue())
            .build())
        .collect(Collectors.toList());
  }

  private IntOrString mapIntOrString(io.fabric8.kubernetes.api.model.IntOrString port) {
    return IntOrString.newBuilder()
        .setIntVal(port.getIntVal())
        .setStrVal(port.getStrVal())
        .build();
  }

  private Iterable<? extends VolumeDevice> mapVolumeDevices(
      List<io.fabric8.kubernetes.api.model.VolumeDevice> volumeDevices) {
    return volumeDevices.stream()
        .map(d -> VolumeDevice.newBuilder()
            .setName(d.getName())
            .setDevicePath(d.getDevicePath())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends VolumeMount> mapVolumeMounts(
      List<io.fabric8.kubernetes.api.model.VolumeMount> volumeMounts) {
    return volumeMounts.stream()
        .map(m -> VolumeMount.newBuilder()
            .setName(m.getName())
            .setReadOnly(m.getReadOnly())
            // FIXME mismatch
            // .setRecursiveReadOnly()
            .setMountPath(m.getMountPath())
            .setSubPath(m.getSubPath())
            .setMountPropagation(m.getMountPropagation())
            .setSubPathExpr(m.getSubPathExpr())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends ContainerResizePolicy> mapContainerResizePolicy(
      List<io.fabric8.kubernetes.api.model.ContainerResizePolicy> resizePolicy) {
    return resizePolicy.stream()
        .map(p -> ContainerResizePolicy.newBuilder()
            .setResourceName(p.getResourceName())
            .setRestartPolicy(p.getRestartPolicy())
            .build())
        .collect(Collectors.toList());
  }

  private ResourceRequirements mapResourceRequirements(
      io.fabric8.kubernetes.api.model.ResourceRequirements resources) {
    ResourceRequirements.Builder builder = ResourceRequirements.newBuilder();

    Map<String, io.fabric8.kubernetes.api.model.Quantity> limits = resources.getLimits();
    if (Objects.nonNull(limits) && !limits.isEmpty()) {
      builder.putAllLimits(mapResourceLimits(limits));
    }

    Map<String, io.fabric8.kubernetes.api.model.Quantity> requests = resources.getRequests();
    if (Objects.nonNull(requests) && !requests.isEmpty()) {
      builder.putAllRequests(mapResourceRequests(requests));
    }

    return builder.build();
  }

  private Quantity mapQuantity(io.fabric8.kubernetes.api.model.Quantity quantity) {
    return Quantity.newBuilder()
        .setString(quantity.getAmount())
        .build();
  }

  private Map<String, Quantity> mapResourceRequests(
      Map<String, io.fabric8.kubernetes.api.model.Quantity> requests) {
    return requests.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> mapQuantity(e.getValue())));
  }

  private Map<String, Quantity> mapResourceLimits(
      Map<String, io.fabric8.kubernetes.api.model.Quantity> limits) {
    return limits.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> Quantity.newBuilder()
            .setString(e.getValue().getAmount())
            .build()));
  }

  private Iterable<? extends EnvFromSource> mapEnvFromSource(
      List<io.fabric8.kubernetes.api.model.EnvFromSource> envFrom) {
    return envFrom.stream()
        .map(e -> {
          EnvFromSource.Builder builder = EnvFromSource.newBuilder();

          io.fabric8.kubernetes.api.model.ConfigMapEnvSource configMapRef = e.getConfigMapRef();
          if (Objects.nonNull(configMapRef)) {
            builder.setConfigMapRef(mapConfigMapEnvSource(configMapRef));
          }

          io.fabric8.kubernetes.api.model.SecretEnvSource secretRef = e.getSecretRef();
          if (Objects.nonNull(secretRef)) {
            builder.setSecretRef(mapSecretEnvSource(e.getSecretRef()));
          }

          return builder.build();
        })
        .collect(Collectors.toList());
  }

  private SecretEnvSource mapSecretEnvSource(
      io.fabric8.kubernetes.api.model.SecretEnvSource secretRef) {
    return SecretEnvSource.newBuilder()
        .setLocalObjectReference(mapLocalObjectReference(secretRef.getName()))
        .setOptional(secretRef.getOptional())
        .build();
  }

  private ConfigMapEnvSource mapConfigMapEnvSource(
      io.fabric8.kubernetes.api.model.ConfigMapEnvSource configMapRef) {
    return ConfigMapEnvSource.newBuilder()
        .setLocalObjectReference(mapLocalObjectReference(configMapRef.getName()))
        .setOptional(configMapRef.getOptional())
        .build();
  }

  private Iterable<? extends EnvVar> mapEnvVars(List<io.fabric8.kubernetes.api.model.EnvVar> env) {
    return env.stream()
        .map(e -> EnvVar.newBuilder()
            .setName(e.getName())
            .setValue(e.getValue())
            .setValueFrom(mapEnvVarSource(e.getValueFrom()))
            .build())
        .collect(Collectors.toList());
  }

  private EnvVarSource mapEnvVarSource(io.fabric8.kubernetes.api.model.EnvVarSource valueFrom) {
    return EnvVarSource.newBuilder()
        .setFieldRef(mapObjectFieldSelector(valueFrom.getFieldRef()))
        .setResourceFieldRef(mapResourceFieldSelector(valueFrom.getResourceFieldRef()))
        .setConfigMapKeyRef(mapConfigMapKeySelector(valueFrom.getConfigMapKeyRef()))
        .setSecretKeyRef(mapSecretKeySelector(valueFrom.getSecretKeyRef()))
        .build();
  }

  private SecretKeySelector mapSecretKeySelector(
      io.fabric8.kubernetes.api.model.SecretKeySelector secretKeyRef) {
    return SecretKeySelector.newBuilder()
        .setLocalObjectReference(mapLocalObjectReference(secretKeyRef.getName()))
        .setKey(secretKeyRef.getKey())
        .setOptional(secretKeyRef.getOptional())
        .build();
  }

  private ConfigMapKeySelector mapConfigMapKeySelector(
      io.fabric8.kubernetes.api.model.ConfigMapKeySelector configMapKeyRef) {
    return ConfigMapKeySelector.newBuilder()
        .setLocalObjectReference(mapLocalObjectReference(configMapKeyRef.getName()))
        .setKey(configMapKeyRef.getKey())
        .setOptional(configMapKeyRef.getOptional())
        .build();
  }

  private LocalObjectReference mapLocalObjectReference(String name) {
    return LocalObjectReference.newBuilder()
        .setName(name)
        .build();
  }

  private ResourceFieldSelector mapResourceFieldSelector(
      io.fabric8.kubernetes.api.model.ResourceFieldSelector resourceFieldRef) {
    return ResourceFieldSelector.newBuilder()
        .setContainerName(resourceFieldRef.getContainerName())
        .setResource(resourceFieldRef.getResource())
        .setDivisor(Quantity.newBuilder()
            .setString(resourceFieldRef.getDivisor().getAmount())
            .build())
        .build();
  }

  private ObjectFieldSelector mapObjectFieldSelector(
      io.fabric8.kubernetes.api.model.ObjectFieldSelector fieldRef) {
    return ObjectFieldSelector.newBuilder()
        .setApiVersion(fieldRef.getApiVersion())
        .setFieldPath(fieldRef.getFieldPath())
        .build();
  }

  private Iterable<? extends ContainerPort> mapContainerPorts(
      List<io.fabric8.kubernetes.api.model.ContainerPort> ports) {
    return ports.stream()
        .map(p -> ContainerPort.newBuilder()
            .setName(p.getName())
            .setHostPort(p.getHostPort())
            .setContainerPort(p.getContainerPort())
            .setProtocol(p.getProtocol())
            .setHostIP(p.getHostIP())
            .build())
        .collect(Collectors.toList());
  }

  private Iterable<? extends Volume> mapVolumes(
      List<io.fabric8.kubernetes.api.model.Volume> volumes) {
    List<Volume> vols = new ArrayList<>();

    volumes.forEach(v -> {
      Volume volume = Volume.newBuilder()
          .setName(v.getName())
          .setVolumeSource(mapVolumeSource(v))
          .build();
      vols.add(volume);
    });

    return vols;
  }

  private VolumeSource mapVolumeSource(io.fabric8.kubernetes.api.model.Volume volume) {
    VolumeSource.Builder builder = VolumeSource.newBuilder();

    if (Objects.nonNull(volume.getEmptyDir())) {
      builder.setEmptyDir(mapEmptyDirVolumeSource(volume));
    }

    if (Objects.nonNull(volume.getSecret())) {
      builder.setSecret(mapSecretVolumeSource(volume));
    }

//    builder
//        .setHostPath(mapHostPathVolumeSource(volume))
//        .setGcePersistentDisk(mapGcePersistentDiskVolumeSource(volume))
//        .setAwsElasticBlockStore(mapAwsElasticBlockStoreVolumeSource(volume))
//        .setGitRepo(mapGitRepoVolumeSource(volume))
//        .setSecret(mapSecretVolumeSource(volume))
//        .setNfs(mapNfsVolumeSource(volume))
//        .setIscsi(mapIscsiVolumeSource(volume))
//        .setGlusterfs(mapGlueterfs(volume))
//        .setPersistentVolumeClaim(mapPersistentVolumeClaim(volume))
//        .setRbd(mapRbdVolumeSource(volume))
//        .setFlexVolume(mapFlexVolume(volume));
    ;

    return builder.build();
  }

  private FlexVolumeSource mapFlexVolume(io.fabric8.kubernetes.api.model.Volume volume) {
    return FlexVolumeSource.newBuilder()
        .setDriver(volume.getFlexVolume().getDriver())
        .setFsType(volume.getFlexVolume().getFsType())
        .setSecretRef(mapLocalObjectReference(volume.getFlexVolume().getSecretRef()))
        .setReadOnly(volume.getFlexVolume().getReadOnly())
        .putAllOptions(volume.getFlexVolume().getOptions())
        .build();
  }

  private RBDVolumeSource mapRbdVolumeSource(io.fabric8.kubernetes.api.model.Volume volume) {
    return RBDVolumeSource.newBuilder()
        // FIXME
        // .setMonitors(volume.getRbd().getMonitors()) type mismatch
        .setImage(volume.getRbd().getImage())
        .setFsType(volume.getRbd().getFsType())
        .setPool(volume.getRbd().getPool())
        .setUser(volume.getRbd().getUser())
        .setKeyring(volume.getRbd().getKeyring())
        .setSecretRef(mapLocalObjectReference(volume.getRbd().getSecretRef()))
        .setReadOnly(volume.getRbd().getReadOnly())
        .build();
  }

  private PersistentVolumeClaimVolumeSource mapPersistentVolumeClaim(
      io.fabric8.kubernetes.api.model.Volume volume) {
    return PersistentVolumeClaimVolumeSource.newBuilder()
        .setClaimName(volume.getPersistentVolumeClaim().getClaimName())
        .setReadOnly(volume.getPersistentVolumeClaim().getReadOnly())
        .build();
  }

  private GlusterfsVolumeSource mapGlueterfs(io.fabric8.kubernetes.api.model.Volume volume) {
    return GlusterfsVolumeSource.newBuilder()
        .setEndpoints(volume.getGlusterfs().getEndpoints())
        .setPath(volume.getGlusterfs().getPath())
        .setReadOnly(volume.getGlusterfs().getReadOnly())
        .build();
  }

  private ISCSIVolumeSource mapIscsiVolumeSource(io.fabric8.kubernetes.api.model.Volume volume) {
    return ISCSIVolumeSource.newBuilder()
        .setTargetPortal(volume.getIscsi().getTargetPortal())
        .setIqn(volume.getIscsi().getIqn())
        .setLun(volume.getIscsi().getLun())
        .setIscsiInterface(volume.getIscsi().getIscsiInterface())
        .setFsType(volume.getIscsi().getFsType())
        .setReadOnly(volume.getIscsi().getReadOnly())
        .addAllPortals(volume.getIscsi().getPortals())
        .setChapAuthDiscovery(volume.getIscsi().getChapAuthDiscovery())
        .setChapAuthSession(volume.getIscsi().getChapAuthSession())
        .setSecretRef(mapLocalObjectReference(volume.getIscsi().getSecretRef()))
        .setInitiatorName(volume.getIscsi().getInitiatorName())
        .build();
  }

  private LocalObjectReference mapLocalObjectReference(
      io.fabric8.kubernetes.api.model.LocalObjectReference secretRef) {
    return LocalObjectReference.newBuilder()
        .setName(secretRef.getName())
        .build();
  }

  private NFSVolumeSource mapNfsVolumeSource(io.fabric8.kubernetes.api.model.Volume volume) {
    return NFSVolumeSource.newBuilder()
        .setServer(volume.getNfs().getServer())
        .setPath(volume.getNfs().getPath())
        .setReadOnly(volume.getNfs().getReadOnly())
        .build();
  }

  private SecretVolumeSource mapSecretVolumeSource(io.fabric8.kubernetes.api.model.Volume volume) {
    return SecretVolumeSource.newBuilder()
        .setSecretName(volume.getSecret().getSecretName())
        .addAllItems(mapKeyToPaths(volume.getSecret().getItems()))
        .setDefaultMode(volume.getSecret().getDefaultMode())
        .setOptional(volume.getSecret().getOptional())
        .build();
  }

  private Iterable<? extends KeyToPath> mapKeyToPaths(
      List<io.fabric8.kubernetes.api.model.KeyToPath> items) {
    return items.stream()
        .map(i -> KeyToPath.newBuilder()
            .setKey(i.getKey())
            .setPath(i.getPath())
            .build())
        .collect(Collectors.toList());
  }

  private GitRepoVolumeSource mapGitRepoVolumeSource(
      io.fabric8.kubernetes.api.model.Volume volume) {
    return GitRepoVolumeSource.newBuilder()
        .setRepository(volume.getGitRepo().getRepository())
        .setRevision(volume.getGitRepo().getRevision())
        .setDirectory(volume.getGitRepo().getDirectory())
        .build();
  }

  private AWSElasticBlockStoreVolumeSource mapAwsElasticBlockStoreVolumeSource(
      io.fabric8.kubernetes.api.model.Volume volume) {
    return AWSElasticBlockStoreVolumeSource.newBuilder()
        .setVolumeID(volume.getAwsElasticBlockStore().getVolumeID())
        .setFsType(volume.getAwsElasticBlockStore().getFsType())
        .setPartition(volume.getAwsElasticBlockStore().getPartition())
        .setReadOnly(volume.getAwsElasticBlockStore().getReadOnly())
        .build();
  }

  private GCEPersistentDiskVolumeSource mapGcePersistentDiskVolumeSource(
      io.fabric8.kubernetes.api.model.Volume volume) {
    return GCEPersistentDiskVolumeSource.newBuilder()
        .setPdName(volume.getGcePersistentDisk().getPdName())
        .setFsType(volume.getGcePersistentDisk().getFsType())
        .setPartition(volume.getGcePersistentDisk().getPartition())
        .setReadOnly(volume.getGcePersistentDisk().getReadOnly())
        .build();
  }

  private EmptyDirVolumeSource mapEmptyDirVolumeSource(
      io.fabric8.kubernetes.api.model.Volume volume) {
    return EmptyDirVolumeSource.newBuilder()
        .setMedium(volume.getEmptyDir().getMedium())
        .setSizeLimit(Quantity.newBuilder()
            .setString(volume.getEmptyDir().getSizeLimit().getAmount()).build())
        .build();
  }

  private HostPathVolumeSource mapHostPathVolumeSource(
      io.fabric8.kubernetes.api.model.Volume volume) {
    if (volume.getHostPath() == null) {
      return HostPathVolumeSource.newBuilder().build();
    }

    return HostPathVolumeSource.newBuilder()
        .setPath(volume.getHostPath().getPath())
        .setType(volume.getHostPath().getType())
        .build();
  }

}
